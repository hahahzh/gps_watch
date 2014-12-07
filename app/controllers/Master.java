package controllers;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.mail.internet.InternetAddress;

import models.BSLocation;
import models.CheckDigit;
import models.ClientVersion;
import models.Customer;
import models.ElectronicFence;
import models.Location;
import models.Log;
import models.Production;
import models.RWatch;
import models.SN;
import models.Session;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import play.Play;
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
 * 儿童手表(定位器)主接口
 * 
 * @author hanzhao
 * 
 */
public class Master extends Controller {
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 定义返回Code
	public static final String SUCCESS = "1";//成功
	public static final String FAIL = "0"; // 失败
	
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	
	public static final int upgrade_flag = 1;// 不用升级
	public static final int error_parameter_required = 1;// 缺少必须参数
	public static final int error_username_already_used = 2;// 已存在的用户名
	public static final int error_username_not_exist = 3;// 不存在用户名
	public static final int error_userid_not_exist = 4;// 用户ID不存在
	public static final int error_not_owner = 5;// 不是定位器的拥有者
	public static final int error_unknown = 6;// 未知错误
	public static final int error_rwatch_not_exist = 7;// 定位器不存在
	public static final int error_both_email_phonenumber_empty = 8;// 电话号码或Email为空
	public static final int error_username_or_password_not_match = 9;// 用户名或密码不匹配
	public static final int error_session_expired = 10;// 会话过期
	public static final int error_mail_resetpassword = 11;// 密码重置错误
	public static final int error_rwatch_bind_full = 12;// 不能绑定过多定位器
	public static final int error_rwatch_already_bind = 13;// 定位器已被绑定
	public static final int error_unknown_waring_format = 14;// 未知警报
	public static final int error_unknown_command = 15;// 未知命令
	public static final int error_rwatch_not_confirmed = 16;// 定位器未确认
	public static final int error_dateformat = 17;// 日期格式错误
	public static final int error_rwatch_max = 18;// 拥有定位器过多
	public static final int error_download = 19;// 下载错误
	public static final int error_send_mail_fail = 20;// 发送Email错误
	public static final int error_already_exists = 21;// 已存在

	// 存储Session副本
	private static ThreadLocal<Session> sessionCache = new ThreadLocal<Session>();
	
	/**
	 * 校验Session是否过期
	 * 
	 * @param sessionID
	 */
	@Before(unless={"checkDigit", "register", "login", "sendResetPasswordMail", "update", "download", "addRWatch", "webBindingWatch", "setRWatch", "receiver"},priority=1)
	public static void validateSessionID(@Required String z) {
		
		Session s = Session.find("bySessionID",z).first();
		sessionCache.set(s);
		if (s == null) {
			renderFail("error_session_expired");
		}
	}
	
	/**
	 * 发送验证码到手机
	 * 
	 * @param m_number
	 */
	public static void checkDigit(@Required String m_number) {
		// ....
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		if(!Validation.phone(SUCCESS, m_number).ok){
			renderFail("error_parameter_required");
		}
		Random r = new Random();
		int n = Math.abs(r.nextInt())/10000;
		
		try {
			boolean s = SendSMS.sendMsg(new String[]{m_number}, "您的验证码是：" + n + "。请不要把验证码泄露给其他人。");
			if(!s){
				play.Logger.error("checkDigit: result="+s+" PNumber="+m_number+" digit="+n);
				renderText("验证码获取失败请稍后再试");
			}
			
			CheckDigit cd = CheckDigit.find("m=?", m_number).first();
			if(cd == null)cd = new CheckDigit();
			cd.d = n;
			cd.updatetime = new Date().getTime();
			cd.m = m_number;
			cd._save();
		} catch (Exception e) {
			play.Logger.error("checkDigit: PNumber="+m_number+" digit="+n);
			play.Logger.error(e.getMessage());
			renderText("系统繁忙发送失败请再次获取");
		}
		renderText("OK");
	}
	
	/**
	 * 用户注册
	 * 
	 * @param z
	 */
	public static void register(@Required String z) {
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}

		try {			
			byte[] b = Coder.decryptBASE64(z);
			String src = new String(b);
			String[] arr = src.split("\\|");
		
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

			Customer m = Customer.find("byM_number", arr[6].trim()).first();
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
			m.m_number = arr[6].trim();
			m.pwd = arr[8];
			m.updatetime = new Date();
			m.save();
			
			c.delete();
			
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
	 * 登录
	 * 
	 * @param phone
	 * @param pwd
	 * @param os
	 * @param serialNumber
	 * @param ip
	 * @param imei
	 * @param mac
	 * @param imsi
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
	 * 登出
	 * 
	 * @param z
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

	/**
	 * 重置密码
	 * 
	 * @param m
	 * @throws UnsupportedEncodingException
	 */
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

	/**
	 * 更新用户信息
	 * 
	 * @param os
	 * @param nickname
	 * @param pwd
	 * @param gender
	 * @param email
	 * @param city
	 * @param birthday
	 * @param height
	 * @param weight
	 * @param weixin
	 * @param step
	 * @param portrait
	 * @param z
	 */
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

	/**
	 * 获取用户信息
	 * 
	 * @param z
	 */
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

	/**
	 * 设置儿童手表(定位器)信息
	 * 
	 * @param imei
	 * @param channel
	 * @param w_type
	 * @param nickname
	 * @param w_number
	 * @param guardian_number1
	 * @param sn
	 * @param guardian_number2
	 * @param guardian_number3
	 * @param guardian_number4
	 * @param rId
	 * @param z
	 */
	public static void setRWatch(String imei, String channel, String w_type, 
			String nickname, String w_number, String guardian_number1, String sn, String guardian_number2, 
			String guardian_number3, String guardian_number4, Long rId, String z) {
		RWatch r = null;
		if(!StringUtil.isEmpty(sn)){
			r = RWatch.find("bySerialNumber", sn).first();
		}
		if(rId != null){
			r = RWatch.findById(rId);
			Session s = Session.find("bySessionID",z).first();
			if(s == null)renderFail("error_session_expired");
		}
		if(r == null){
			renderFail("error_rwatch_not_exist");
		}
		
		if(!StringUtil.isEmpty(imei)){
			r.imei = imei;
		}
		if(!StringUtil.isEmpty(w_type)){
			Production p = Production.find("byP_name", w_type).first();
			if(p != null)r.production = p;
		}
		if(!StringUtil.isEmpty(channel)){
			r.channel = channel;
		}
		if (nickname != null && !"".equals(nickname)){
			r.nickname = nickname;
		}
		if (!StringUtil.isEmpty(w_number)){
			r.m_number = w_number;
		}
		if (!StringUtil.isEmpty(guardian_number1)){
			r.guardian_number1 = guardian_number1;
		}
		if (!StringUtil.isEmpty(guardian_number2)){
			r.guardian_number2 = guardian_number1;
		}
		if (!StringUtil.isEmpty(guardian_number3)){
			r.guardian_number3 = guardian_number1;
		}
		if (!StringUtil.isEmpty(guardian_number4)){
			r.guardian_number4 = guardian_number1;
		}
		r._save();
		JSONObject results = initResultJSON();
		renderSuccess(results);
	}
	
	/**
	 * 获取当前用户绑定的所有儿童手表(定位器)
	 * 
	 * @param z
	 */
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
		List<RWatch> rwatchs = RWatch.find("c_id=? or guardian_number1=? or guardian_number2=? or guardian_number3=? or guardian_number4=?", c.id, c.m_number, c.m_number, c.m_number, c.m_number).fetch();
		
		JSONObject results = initResultJSON();
		JSONArray datalist = initResultJSONArray();
		if (!rwatchs.isEmpty()) {
			for(RWatch r : rwatchs){
				JSONObject data = initResultJSON();
				data.put("rId", r.id);
				data.put("imei", r.imei);
				data.put("m_number", r.m_number);
				data.put("nickname", r.nickname);
				data.put("guardian_number1", r.guardian_number1);
				data.put("guardian_number2", r.guardian_number2);
				data.put("guardian_number3", r.guardian_number3);
				data.put("guardian_number4", r.guardian_number4);
				data.put("bindDate", DateUtil.reverseDate(r.bindDate,3));
				data.put("production", r.production.p_name);
				data.put("sn", r.serialNumber);
				ElectronicFence ef = ElectronicFence.find("byRWatch", r).first();
				if(ef != null){
					data.put("electronicFence_lat", ef.lat);
					data.put("electronicFence_lon", ef.lon);
					data.put("electronicFence_radius", ef.radius);
					data.put("electronicFence_datetime", DateUtil.reverseDate(ef.dateTime,3));
					data.put("electronicFence_status", ef.on);
				}
				datalist.add(data);
			}
		}
		results.put("list", datalist);
		renderSuccess(results);
	}
	
	/**
	 * 接受GPS坐标信息
	 * 
	 * @param datas
	 */
	public static void receiver(@Required String datas) {
		// 参数验证
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		
		RWatch r = null;
		Production p = null;
		BSLocation bsl = null;
		ElectronicFence ef = null;
		String tmpStr = null;
		try {
			tmpStr = new String(Coder.decryptBASE64(datas));
			if(StringUtil.isEmpty(tmpStr))renderText(3);
		} catch (Exception e) {
			e.printStackTrace();
			renderText(3);
		}
		String[] tmpArray = tmpStr.split(",");
		int length = tmpArray.length==0?1:tmpArray.length;
		
		for(int j=0;j<length;j++){
			String[] dataArray = tmpArray[j].split("\\|");
			String sn = dataArray[1];
			if(r == null){
				r = RWatch.find("bySerialNumber", sn).first();
				if(r == null || !r.isPersistent())renderText(1);
			}
			
			String rt = dataArray[2];
			if(p == null){
				p = Production.find("byP_name", rt).first();
				if(p == null || !p.isPersistent())renderText(2);
			}
			
			Location l = new Location();
			l.rwatch = r;
			
			String datetime = dataArray[3];
			double latitude = Double.parseDouble(dataArray[4]);
			int latitudeFlag = Integer.parseInt(dataArray[5]);
			double longitude = Double.parseDouble(dataArray[6]);
			int longitudeFlag = Integer.parseInt(dataArray[7]);
			double speed = Double.parseDouble(dataArray[8]);
			int direction = Integer.parseInt(dataArray[9]);
			String status  = dataArray[10];
			int mcc = Integer.parseInt(dataArray[11]);
			int mnc = Integer.parseInt(dataArray[12]);
			int cellid = Integer.parseInt(dataArray[13]);
			int lac = Integer.parseInt(dataArray[14]);
			String signal1 = dataArray[15];
			
			l.dateTime = datetime;
			l.latitude = latitude;
			l.latitudeFlag = latitudeFlag;
			l.longitude = longitude;
			l.longitudeFlag = longitudeFlag;
			l.speed = speed;
			l.direction = direction;
			l.status = status;
			l.mcc = mcc;
			l.mnc = mnc;
			l.lac = lac;
			l.cellid = cellid;
			bsl = BSLocation.find("mcc=? and mnc=? and lac=? and cellid=?", mcc,mnc,lac,cellid).first();
			if(bsl != null && bsl.isPersistent()){
				l.cell_accuracy = bsl.cell_accuracy;
				l.cell_coordinateType = bsl.cell_coordinateType;
				l.cell_latitude = bsl.latitude;
				l.cell_longitude = bsl.longitude;
			}
			l.signal1 = signal1;
			l.receivedTime = new Date();
			
			if(ef == null)ef = ElectronicFence.find("byRWatch", r).first();
			if(ef != null){
				if(ef.on == 0)l.valid = 0;
				else if(ef.on == 1){
					if(new BigDecimal(StringUtil.distanceBetween(latitude, longitude, ef.lat, ef.lon)).compareTo(new BigDecimal(ef.radius)) > 0){
						l.valid = 2;
					}else{
						l.valid = 1;
					}
				}
			}
			l._save();
		}
		renderText(0);
	}

	/**
	 * 获取GPS信息
	 * 
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param num
	 * @param rId
	 * @param z
	 */
	public static void getLocation( String startTime, String endTime, Integer page,Integer num, @Required Long rId, @Required String z) {
		
		// 参数验证
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		
		Session s = sessionCache.get();
		if(s == null){
			renderFail("error_session_expired");
		}
		
		int begin = 0;
		if(num == null){
			num = 100;
		}
		num = num > 100? 100:num;
		if(page != null && page > 1){
			begin = (page-1)*num;
		}
		
		List<Location> locations = null;
		Date startTimeDate = null;
		Date endTimeDate = null;
		try {
			if (startTime != null) {
				startTimeDate = sdf.parse(startTime);
			}
			if (endTime != null) {
				endTimeDate = sdf.parse(endTime);
			}
			
			if(startTimeDate != null && endTimeDate != null){
				locations = Location.find("rwatch_id=? and (receivedTime>? and receivedTime<?)", rId, startTimeDate, endTimeDate).fetch(begin, num);
			}else if(startTimeDate != null && endTimeDate == null){
				locations = Location.find("rwatch_id=? and receivedTime>?", rId, startTimeDate).fetch(begin, num);
			}else if(startTimeDate == null && endTimeDate != null){
				locations = Location.find("rwatch_id=? and receivedTime<?", rId, endTimeDate).fetch(begin, num);
			}else if(startTimeDate == null && endTimeDate == null){
				locations = Location.find("rwatch_id=?", rId).fetch(begin, num);
			}
			
		} catch (Exception e) {
			renderFail("error_dateformat", error_dateformat);
		}
		
		JSONObject results = initResultJSON();
		JSONArray datalist = initResultJSONArray();
		
		if (!locations.isEmpty()) {
			for(Location l : locations){
				JSONObject data = initResultJSON();
				data.put("id", l.id);
				data.put("rwatchId", l.rwatch.id);
				data.put("dateTime", l.dateTime);
				data.put("latitude", l.latitude);
				data.put("longitude", l.longitude);
				data.put("cell_latitude", l.cell_latitude);
				data.put("cell_longitude", l.cell_longitude);
				data.put("cell_accuracy", l.cell_accuracy);
				data.put("cell_coordinateType", l.cell_coordinateType);
				data.put("speed", l.speed);
				data.put("direction", l.direction);
				data.put("dateTime", l.dateTime);
				data.put("status", l.status);
				data.put("mcc", l.mcc);
				data.put("mnc", l.mnc);
				data.put("lac", l.lac);
				data.put("cellid", l.cellid);
				data.put("latitudeFlag", l.latitudeFlag);
				data.put("longitudeFlag", l.longitudeFlag);
				data.put("waring", l.valid);
				datalist.add(data);
			}
		}
		results.put("list", datalist);
		renderSuccess(results);
	}
	
	/**
	 * 重置密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @param z
	 */
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
	
	/**
	 * 设置电子围栏信息
	 * 
	 * @param rId
	 * @param on
	 * @param lon
	 * @param lat
	 * @param radius
	 * @param z
	 */
	public static void setElectronicFence(@Required Long rId, @Required Integer on, Double lon, Double lat, Double radius, @Required String z) {
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		JSONObject results = initResultJSON();
		RWatch rWatch = RWatch.findById(rId);
		if(rWatch == null)renderFail("error_rwatch_not_exist");
		ElectronicFence ef = ElectronicFence.find("byRWatch", rWatch).first();
		if (ef == null) {
			ef = new ElectronicFence();
			ef.rWatch = rWatch;
		}
		ef.dateTime = new Date();
		ef.on = on;
		if (on == 0) {
			if (lat != null) {
				ef.lat = lat;
			}
			if (lon != null) {
				ef.lon = lon;
			}
			if (radius != null) {
				ef.radius = radius;
			}
		}
		ef.save();
		renderSuccess(results);
	}
	
	/**
	 * 绑定儿童手表(定位器)
	 * 
	 * @param m_number
	 * @param w_number
	 * @param sn
	 */
	public static void addRWatch(@Required String m_number, @Required String w_number, @Required String sn) {
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		JSONObject results = initResultJSON();
		if(SN.count("sn=?", sn) < 1){
			renderFail("error_rwatch_not_exist");
		}
		Customer c = Customer.find("byM_number", m_number).first();
		if(c == null){
			renderFail("error_userid_not_exist");
		}
		long count = RWatch.count("guardian_number1=? or guardian_number2=? or guardian_number3=? or guardian_number4=?", c.m_number,c.m_number,c.m_number,c.m_number);
		int tmpMax = Integer.parseInt(Play.configuration.getProperty("rwatch.max"));
		if (tmpMax == 0) {
			tmpMax = 5;
		}
		if (count > tmpMax) {
			renderFail("error_rwatch_max");
		}

		RWatch rWatch = RWatch.find("bySerialNumber", sn).first();
		if (rWatch == null) {
			rWatch = new RWatch();
			rWatch.guardian_number1 = c.m_number;
			rWatch.serialNumber = sn;
			rWatch.m_number = w_number;
			rWatch.c = c;
			rWatch.bindDate = new Date();
		}else if(rWatch.c == null){
			rWatch.guardian_number1 = c.m_number;
			rWatch.serialNumber = sn;
			rWatch.m_number = w_number;
			rWatch.c = c;
			rWatch.bindDate = new Date();
		}else{
			renderFail("error_rwatch_bind_full");
		}
		rWatch.save();
		renderSuccess(results);
	}
	
	/**
	 * 解绑儿童手表(定位器)
	 * 
	 * @param w_number
	 * @param z
	 */
	public static void delRWatch(@Required String w_number, @Required String z) {
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		RWatch rWatch = RWatch.find("byM_number", w_number).first();
		if(rWatch == null)renderFail("error_rwatch_not_exist");
		Session s = sessionCache.get();
		if(s.c.id != rWatch.c.id)renderFail("error_not_owner");
		rWatch.c = null;
		rWatch.save();
		JSONObject results = initResultJSON();
		renderSuccess(results);
	}

	/**
	 * Web绑定儿童手表(定位器)
	 * 
	 * @param m_number
	 * @param w_number
	 * @param sn
	 */
	public static void webBindingWatch(@Required String m_number, @Required String w_number, @Required String sn) {
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		if(!Validation.phone(SUCCESS, m_number).ok || !Validation.phone(SUCCESS, w_number).ok){
			renderFail("error_parameter_required");
		}
		JSONObject results = initResultJSON();
		if(SN.count("sn=?", sn) < 1){
			renderFail("error_rwatch_not_exist");
		}
		Customer c = Customer.find("byM_number", m_number).first();
		if(c == null){
			renderFail("error_userid_not_exist");
		}
		long count = RWatch.count("guardian_number1=? or guardian_number2=? or guardian_number3=? or guardian_number4=?", c.m_number,c.m_number,c.m_number,c.m_number);
		int tmpMax = Integer.parseInt(Play.configuration.getProperty("rwatch.max"));
		if (tmpMax == 0) {
			tmpMax = 5;
		}
		if (count > tmpMax) {
			renderFail("error_rwatch_max");
		}

		try {
			boolean s = SendSMS.sendMsg(new String[]{w_number}, Play.ctxPath+"bindingWatch?sn="+sn+"&m_number="+m_number+"&w_number="+w_number);
			if(!s){
				play.Logger.error("webBindingWatch: result="+s+" PNumber="+m_number+" sn="+sn);
				renderText("验证码获取失败请稍后再试");
			}
		} catch (Exception e) {
			play.Logger.error("webBindingWatch: PNumber="+m_number+" sn="+sn);
			play.Logger.error(e.getMessage());
			renderFail("error_unknown_command");
		}
		
		renderSuccess(results);
	}
	
	/**
	 * 下载
	 * 
	 * @param id
	 * @param fileID
	 * @param entity
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
	 * 获取版本信息
	 * 
	 * @param version
	 * @param m_id
	 * @param m_type
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
