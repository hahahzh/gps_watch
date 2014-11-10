package controllers;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.mail.internet.InternetAddress;

import models.CheckDigit;
import models.ClientVersion;
import models.Customer;
import models.Location;
import models.Log;
import models.Production;
import models.RWatch;
import models.Session;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import play.Play;
import play.cache.Cache;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.db.Model;
import play.db.jpa.Blob;
import play.i18n.Messages;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http.Header;
import utils.Coder;
import utils.DateUtil;
import utils.JSONUtil;
import utils.SendMail;
import utils.SendSMS;
import utils.StringUtil;
import controllers.CRUD.ObjectType;

/**
 * ......
 * 
 * @author hanzhao
 * 
 */
//@With(Compress.class)
public class Master extends Controller {

	public static final String SUCCESS = "1";
	public static final String FAIL = "0";
	
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	
	public static final int upgrade_flag = 1;// .......
	public static final int error_parameter_required = 1;// .......
	public static final int error_username_already_used = 2;// ........
	public static final int error_username_not_exist = 3;// .......
	public static final int error_userid_not_exist = 4;// ..id....
	public static final int error_not_owner = 5;// &{%s} .........
	public static final int error_unknown = 6;// ....,......
	public static final int error_locator_not_exist = 7;// .......
	public static final int error_both_email_phonenumber_empty = 8;// ..............
	public static final int error_username_or_password_not_match = 9;// ..........
	public static final int error_session_expired = 10;// .....,......
	public static final int error_mail_resetpassword = 11;// ..........,.........
															// &{%s} .
	public static final int error_locator_bind_full = 12;// ... &{%s} ..........
	public static final int error_locator_already_bind = 13;// ... &{%s} .......
	public static final int error_unknown_waring_format = 14;// .............
	public static final int error_unknown_command = 15;// ..... &{%s}.
	public static final int error_locator_not_confirmed = 16;// ..........,...........
	public static final int error_dateformat = 17;// .......
	public static final int error_locator_max = 18;// ......
	public static final int error_download = 19;// ....
	public static final int error_send_mail_fail = 20;
	public static final int error_already_exists = 21;// ........
	/**
	 * ...............
	 */
	private static ThreadLocal<Session> sessionCache = new ThreadLocal<Session>();
	
	/**
	 * ....
	 * 
	 * @param sessionID
	 */
	@Before(unless={"checkDigit", "register", "login", "sendResetPasswordMail", "update", "download"},priority=1)
	public static void validateSessionID(@Required String z) {
		
		Session s = Session.find("bySessionID",z).first();
		sessionCache.set(s);
		if (s == null) {
			renderFail("error_session_expired");
		}
	}
	
	public static void checkDigit(@Required String m) {
		// ....
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		if(!Validation.phone(SUCCESS, m).ok){
			renderFail("error_parameter_required");
		}
		Random r = new Random();
		int n = Math.abs(r.nextInt())/10000;
		
		try {
			boolean s = SendSMS.sendMsg(new String[]{m}, "您的验证码是：" + n + "。请不要把验证码泄露给其他人。");
			if(!s){
				play.Logger.error("checkDigit: result="+s+" PNumber="+m+" digit="+n);
				renderText("验证码获取失败请稍后再试");
			}
			
			CheckDigit cd = new CheckDigit();
			cd.d = n;
			cd.updatetime = new Date().getTime();
			cd.m = m;
			cd._save();
		} catch (Exception e) {
			play.Logger.error("checkDigit: PNumber="+m+" digit="+n);
			play.Logger.error(e.getMessage());
			renderText("系统繁忙发送失败请再次获取");
		}
		renderText("OK");
	}
	
	// 注册
	public static void register(@Required String z) {
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}

		try {			
			byte[] b = Coder.decryptBASE64(z);
			String src = new String(b);
			String[] arr = z.split("\\|");
		
			int i = Integer.parseInt(arr[7]);
			CheckDigit c = CheckDigit.find("d=?", i).first();
			if(c == null){
				renderFail("error_checkdigit");
			}
			if(!c.m.equals(arr[6])){
				renderFail("error_checkdigit");
			}
			if(new Date().getTime() - c.updatetime > 1800000){
				c.delete();
				renderFail("error_checkdigit");
			}
			c.delete();

			Customer m = Customer.find("byM_number", arr[6]).first();
			if(m != null){
				play.Logger.info("register:error_username_already_used");
				renderFail("error_username_already_used");
			}
			
			m = new Customer();
			m.cid = arr[1];
			m.mac = arr[2];
			m.imei = arr[3];
			m.imsi = arr[4];
			m.os = Integer.parseInt(arr[5]);
			m.m_number = Integer.parseInt(arr[6]);
			m.pwd = arr[8];
			m.updatetime = new Date();
			m.save();
			
			Session s = new Session();
			s.c = m;
			s.loginUpdatetime = new Date();
			s.sessionID = UUID.randomUUID().toString();
			s.nickname = m.nickname;
			s.save();
			
			JSONObject results = initResultJSON();
			results.put("uid", m.getId());
			results.put("phone", m.m_number);
			results.put("name", m.nickname);
			results.put("session", s.sessionID);
			play.Logger.info("register:OK "+m.m_number+" "+m.mac);
			renderSuccess(results);
		} catch (Exception e) {
			play.Logger.info("register:error "+e.getMessage());
			renderFail("error_unknown");
		}
		
	}
		
	/**
	 * Login
	 * 
	 * @param username
	 * @param password
	 * @param type
	 *            .....,......,iphone.......push..
	 * @param serialNumber
	 *            iphone.......,push..
	 */
	public static void login(@Required String phone,
			@Required String pwd, @Required Integer os,
			String serialNumber, String ip, String imei, String mac, String imsi) {
		// ....
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}

		if (os != null && os == 2 && (serialNumber == null || serialNumber.isEmpty())) {
			renderFail("error_parameter_required");
		}
		Customer c = Customer.find("byM_number", phone).first();
		
		if(c == null){
			c = Customer.find("byEmail", phone).first();
			if(c == null)c = Customer.find("byWeixin", phone).first();
		}
		
		if(c == null || !c.pwd.equals(pwd)){
			renderFail("error_username_or_password_not_match");
		}
		
		Session s = Session.find("byC", c).first();
		if(s == null){
			s = new Session();
			s.c = c;
			s.sessionID = UUID.randomUUID().toString();
			s.nickname = c.nickname;
		}
		s.loginUpdatetime = new Date();
		s._save();
		
		c.updatetime = new Date();
		c.os = os;
		c._save();
		sessionCache.set(s);
		
		JSONObject results = initResultJSON();
		results.put("uid", c.getId());
		results.put("phone", c.m_number);
		results.put("name", c.nickname);
		results.put("session", s.sessionID);
		play.Logger.info("login:OK "+c.m_number+" "+c.mac);
		
		Log l = new Log();
		l.c_id = c.id;
		l.ip = ip;
		l.imei = imei;
		l.imsi = imsi;
		l.mac = mac;
		l.updatetime = new Date();
		l.type = "login";
		l._save();
		
		renderSuccess(results);
	}
	
	
	
	/**
	 * ....
	 * 
	 * @param username
	 * @param password
	 * @param sessionID
	 */
	public static void logout(@Required String z) {
		// ....
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		Session s = sessionCache.get();
		if(s != null && s.id != 1 && s.id != 2){
			s.delete();
			s.sessionID = "";
			s.save();
		}
		renderSuccess(initResultJSON());
	}


	@SuppressWarnings("deprecation")
	public static void sendResetPasswordMail(@Required String m)
			throws UnsupportedEncodingException {

		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}

		Customer c = Customer.find("byM_number", m).first();
		if (c == null) {
			c = Customer.find("byEmail", m).first();
			if(c == null)c = Customer.find("byWeixin", m).first();
			if(c == null)renderFail("error_username_not_exist");
		}
		
		SendMail mail = new SendMail(
				Play.configuration.getProperty("mail.smtp.host"),
				Play.configuration.getProperty("mail.smtp.user"),
				Play.configuration.getProperty("mail.smtp.pass"));

		mail.setSubject(Messages.get("mail_resetpassword_title"));
		mail.setBodyAsText("phone:"+c.m_number+"password:"+c.pwd);

		String nick = Messages.get("mail_show_name");
		try {
			nick = javax.mail.internet.MimeUtility.encodeText(nick);
			mail.setFrom(new InternetAddress(nick + " <"
					+ Play.configuration.getProperty("mail.smtp.from") + ">")
					.toString());
			mail.setTo(c.email);
			mail.send();
		} catch (Exception e) {
			renderFail("error_mail_resetpassword");
		}
		renderSuccess(initResultJSON());
	}


	//更新用户信息
	public static void updateMemberInfo(Integer os, String nickname, String pwd, String gender, String email, 
			String city, Date birthday, String height, String weight, String weixin, String step, Blob portrait, @Required String z) {

		// ....
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}

		Session s = sessionCache.get();
		Customer c = s.c;
		
		if(os != null){
			c.os = os;
		}
		if(nickname != null && !nickname.isEmpty()){
			c.nickname = nickname;
		}
		if(email != null && !email.isEmpty()){
			c.email = email;
		}
		if(pwd != null && !pwd.isEmpty()){
			c.pwd = pwd;
		}
		if(gender != null){
			c.gender = Byte.valueOf(gender);
		}
		if(city != null){
			c.city = city;
		}
		if(birthday != null){
			c.birthday = birthday;
		}
		if(height != null){
			c.height = height;
		}
		if(weight != null){
			c.weight = weight;
		}
		if(!StringUtil.isEmpty(weixin)){
			c.weixin = weixin;
		}
		if(!StringUtil.isEmpty(step)){
			c.step = step;
		}
		if(portrait != null){
			if(c.portrait.exists()){
				c.portrait.getFile().delete();
			}
			c.portrait = portrait;
		}
		c.save();
		renderSuccess(initResultJSON());
	}

	// 得到用户信息
	public static void getMemberInfo(@Required String z) {
		
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		
		Session s = sessionCache.get();
		if(s == null){
			renderFail("error_session_expired");
		}
		
		Customer c = s.c;
		JSONObject results = initResultJSON();
	
		results.put("phonenumber", c.m_number+"");
		results.put("nickname", c.nickname);
		results.put("email", c.email);
		results.put("os", c.os+"");
		results.put("gender", c.gender+"");
		results.put("city", c.city);
		results.put("height", c.height);
		results.put("weight", c.weight);
		results.put("step", c.step);
		results.put("weixin", c.weixin);
		results.put("birthday", DateUtil.reverseDate(c.birthday,1));
		
		if(c.portrait != null && c.portrait.exists()){
			results.put("portrait", "/c/download?id=" + c.id + "&fileID=portrait&entity=" + c.getClass().getName() + "&z=" + z);
		}else{
			if(c.gender == null || c.gender == 0){
				results.put("portrait", "/public/images/boy.jpg");
			}else{
				results.put("portrait", "/public/images/girl.jpg");
			}
		}
		renderSuccess(results);
	}

	public static void setRWatch(String imei, Long rId, String p_name, String nickname, String m_number, Integer guardian_number1, Integer guardian_number2, Integer guardian_number3, Integer guardian_number4, @Required String z) {
		
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		
		Session s = sessionCache.get();
		if(s == null){
			renderFail("error_session_expired");
		}
		
		RWatch r = null;
		if(!StringUtil.isEmpty(imei)){
			r = new RWatch();
			r.imei = imei;
		}else{
			if(rId != null)	r = RWatch.findById(rId);
			if(r == null)renderFail("error_rwatch_not_exist");
		}
		
		if (nickname != null && !"".equals(nickname)){
			r.nickname = nickname;
		}
		if (m_number != null && !"".equals(m_number)){
			r.m_number = m_number;
		}
		if (guardian_number1 != null){
			r.guardian_number1 = guardian_number1;
		}
		if (guardian_number2 != null){
			r.guardian_number1 = guardian_number1;
		}
		if (guardian_number3 != null){
			r.guardian_number1 = guardian_number1;
		}
		if (guardian_number4 != null){
			r.guardian_number1 = guardian_number1;
		}
		if(p_name != null && !"".equals(p_name)){
			r.production = Production.find("p_name=?",p_name).first();
		}
		r.c = s.c;
		r.bindDate = new Date();
		r._save();
		JSONObject results = initResultJSON();
		renderSuccess(results);
	}
	
	public static void getRWatchList(@Required String z) {
		
		// 参数验证
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}

		Session s = sessionCache.get();
		if(s == null){
			renderFail("error_session_expired");
		}
		
		Customer c = s.c;

		List<RWatch> rwatchs = RWatch.find("byC", c).fetch();
		
		JSONObject results = initResultJSON();
		JSONArray datalist = initResultJSONArray();
		if (!rwatchs.isEmpty()) {
			for(RWatch r : rwatchs){
				JSONObject data = initResultJSON();
				data.put("imei", r.imei);
				data.put("m_number", r.m_number);
				data.put("nickname", r.nickname);
				data.put("guardian_number1", r.guardian_number1);
				data.put("guardian_number2", r.guardian_number2);
				data.put("guardian_number3", r.guardian_number3);
				data.put("guardian_number4", r.guardian_number4);
				data.put("bindDate", DateUtil.reverseDate(r.bindDate,3));
				data.put("production", r.production.p_name);
				datalist.add(data);
			}
		}
		results.put("list", datalist);
		renderSuccess(results);
	}
	
	public static void getLocation(String userName,	String password, @Required Long rId, String z) {
		
		// 参数验证
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		
		Session s = sessionCache.get();
		if(s == null){
			renderFail("error_session_expired");
		}
		
		JSONObject results = initResultJSON();
		JSONArray datalist = initResultJSONArray();
		List<Location> locations = Location.find("rwatch_id=?", rId).fetch(5);
		if (!locations.isEmpty()) {
			for(Location l : locations){
				JSONObject data = initResultJSON();
				data.put("dateTime", l.dateTime);
				data.put("latitude", l.latitude);
				data.put("longitude", l.longitude);
				data.put("rwatchId", l.rwatch.id);
				datalist.add(data);
			}
		}
		results.put("list", datalist);
		renderSuccess(results);
	}
	
	public static void insertLocation(@Required Long rId, Date dateTime, double latitude, double longitude, Integer mcc, Integer mnc, Integer lac, @Required String z) {
		
		// 参数验证
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		
		RWatch r = RWatch.findById(rId);
		if(r != null){
			Location l = new Location();
			l.dateTime = dateTime;
			l.lac = lac;
			l.mcc = mcc;
			l.mnc = mnc;
			l.latitude = latitude;
			l.longitude = longitude;
			l.receivedTime = new Date();
			l.rwatch = r;
			l._save();
		}
		
		JSONObject results = initResultJSON();
		renderSuccess(results);
	}
	
	public static void changePassword(@Required String oldPassword, @Required String newPassword, @Required String z) {
		JSONObject results = initResultJSON();
		// ....
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		Session s = Session.find("bySessionID",z).first();
		if(s.c.pwd.equals(oldPassword) && !StringUtil.isEmpty(newPassword)){
			s.c.pwd = newPassword;
			s.c._save();
			renderSuccess(results);
		}else{
			renderFail("error_old_password_not_match");
		}
		

	}

	public static void clearCache(@Required String z) {
		JSONObject results = initResultJSON();
		// ....
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}

		Cache.clear();
		renderSuccess(results);

	}

	/**
	 * ......
	 * 
	 * @param username
	 *            ...
	 * @param password
	 *            ..
	 * @param sessionID
	 *            ..
	 * @param fileID
	 *            ..uuid
	 * @param fileName
	 *            ....
	 */
	public static void download(@Required String id, @Required String fileID, @Required String entity) {

		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		ObjectType type;
		try {
			type = new ObjectType(entity);
			notFoundIfNull(type);

			Model object = type.findById(id);
			notFoundIfNull(object);
			Object att = object.getClass().getField(fileID).get(object);
			if (att instanceof Model.BinaryField) {
				Model.BinaryField attachment = (Model.BinaryField) att;
				if (attachment == null || !attachment.exists()) {
					renderFail("error_download");
				}
				long p = 0;
				Header h = request.headers.get("Range");
				play.Logger.info("download header:", h);
				if(h != null){
					p = Long.parseLong(h.value().replaceAll("bytes=", "").replaceAll("-", ""));
				}
				play.Logger.info("download header:", p);
				response.contentType = attachment.type();
				if(p > 0){
					renderBinary(attachment.get(), attachment.get().skip(p));
				}else{
					renderBinary(attachment.get(), attachment.length());
				}
				
			}
		} catch (Exception e) {
			renderText("Download failed");
		}
		renderFail("error_download");
	}

	/**
	 * 版本校验
	 * 
	 * @param version
	 * @param type
	 * @throws JSONException 
	 */
	public static void update(@Required String version, Integer m_id, String m_type) {
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		ClientVersion cv = ClientVersion.find("mobiletype_id = ?", m_id).first();
		if(cv == null){
			cv = ClientVersion.find("mobiletype.type = ?", m_type).first();
		}
		if (cv != null && !cv.version.equals(version)) {
			
			JSONObject results = initResultJSON();
			results.put("url", cv.url);
			results.put("version", cv.version);
			results.put("m_type", cv.mobiletype.type);
			results.put("update", cv.update_desc);
			results.put("apk", "/c/download?id=" + cv.id + "&fileID=apk&entity=" + cv.getClass().getName());
			renderSuccess(results);
		} else {
			renderFail("OK");
		}
	}
	
	protected static JSONObject initResultJSON() {
		return JSONUtil.getNewJSON();
	}
	
	protected static JSONArray initResultJSONArray() {
		return JSONUtil.getNewJSONArray();
	}


	protected static void renderSuccess(JSONObject results) {
		JSONObject jsonDoc = new JSONObject();
		jsonDoc.put("state", SUCCESS);
		jsonDoc.put("results",results);
		renderJSON(jsonDoc.toString());
	}

	protected static void renderFail(String key, Object... objects) {
		JSONObject jsonDoc = new JSONObject();
		jsonDoc.put("state", FAIL);
		jsonDoc.put("msg", Messages.get(key));
		renderJSON(jsonDoc.toString());
	}

}
