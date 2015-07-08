package controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.mail.internet.InternetAddress;

import org.apache.http.client.ClientProtocolException;

import models.AdminManagement;
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
import models.TimeInterval;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import play.Play;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.db.DB;
import play.db.Model;
import play.db.jpa.Blob;
import play.db.jpa.JPA;
import play.i18n.Messages;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http.Header;
import utils.Coder;
import utils.DateUtil;
import utils.HttpRequestSend;
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
	public static final int error_parameter_formate = 22;// 格式错误 

	// 存储Session副本
	public static ThreadLocal<Session> sessionCache = new ThreadLocal<Session>();
	
	/**
	 * 校验Session是否过期
	 * 
	 * @param sessionID
	 */
	@Before(unless={"checkDigit", "register", "login", "sendResetPasswordMail", "update", "download", 
			"addRWatch", "webBindingWatch", "setRWatch", "receiver", "getRWatchInfo", "syncTime", "insertSN", "getRWatchInfo_New"},priority=1)
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
			boolean s = SendSMS.sendMsg(new String[]{m_number}, play.i18n.Messages.get("verification_msg",n));
			if(!s){
				play.Logger.error("checkDigit: result="+s+" PNumber="+m_number+" digit="+n);
				renderText(play.i18n.Messages.get("error_verification_code"));
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
			renderText(play.i18n.Messages.get("error_verification_code_sys"));
		}
		renderText("OK");
	}
	
	public static void sendMsg(@Required String p, @Required String msg,@Required String z) {
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		
		RWatch r = RWatch.find("m_number=?", p).first();
		if(r == null)r = RWatch.find("serialNumber=?", p).first();
		if(r == null)r = RWatch.find("nickname=?", p).first();
		if(r == null)renderFail("error_rwatch_not_exist");
		try {
			boolean s = SendSMS.sendMsg(new String[]{r.m_number}, msg+"");
			if(!s){
				play.Logger.error("sendMsg: result="+s+" PNumber="+r.m_number);
				renderFail("发送短信失败");
			}
		} catch (Exception e) {
			play.Logger.error("sendMsg: PNumber="+r.m_number);
			play.Logger.error(e.getMessage());
			renderFail("发送短信失败");
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
//			s.sessionID = "";
//			s.save();
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
                c = Customer.find("byWeixin", m).first();
                if(c == null){
                	List<Customer> tmp = Customer.find("byEmail", m).fetch();
                	if(tmp.size() == 0)renderFail("error_username_not_exist");
                	SendMail mail = new SendMail(
                            Play.configuration.getProperty("mail.smtp.host"),
                            Play.configuration.getProperty("mail.smtp.user"),
                            Play.configuration.getProperty("mail.smtp.pass"));

                	mail.setSubject(Messages.get("mail_resetpassword_title"));
                	String text = "";
                	for(Customer tmpC : tmp){
                		text += "phone:"+tmpC.m_number+"password:"+tmpC.pwd+"\n";
                	}
                	mail.setBodyAsText(text);

	            String nick = Messages.get("mail_show_name");
	            try {
	                    nick = javax.mail.internet.MimeUtility.encodeText(nick);
	                    mail.setFrom(new InternetAddress(nick + " <"
	                                    + Play.configuration.getProperty("mail.smtp.from") + ">")
	                                    .toString());
	                    mail.setTo(m);
	                    mail.send();
	            } catch (Exception e) {
	                    renderFail("error_mail_resetpassword");
	            }
	            renderSuccess(initResultJSON());
                	
                }
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
			String nickname, String w_number, String sn, String guardian_number1, String guardian_number2, 
			String guardian_number3, String guardian_number4, String name_number1, String name_number2, 
			String name_number3, String name_number4, Long rId, String production, Integer mode,
			String whiteList, Boolean remind_open_close, Boolean remind_low_power, String z) {
		RWatch r = null;
		if(!StringUtil.isEmpty(sn)){
			r = RWatch.find("bySerialNumber", sn).first();
		}
		if(rId != null){
			r = RWatch.find("id=?", rId).first();
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
			r.guardian_number2 = guardian_number2;
		}
		if (!StringUtil.isEmpty(guardian_number3)){
			r.guardian_number3 = guardian_number3;
		}
		if (!StringUtil.isEmpty(guardian_number4)){
			r.guardian_number4 = guardian_number4;
		}
		if (!StringUtil.isEmpty(name_number1)){
			r.name_number1 = name_number1;
		}
		if (!StringUtil.isEmpty(name_number2)){
			r.name_number2 = name_number2;
		}
		if (!StringUtil.isEmpty(name_number3)){
			r.name_number3 = name_number3;
		}
		if (!StringUtil.isEmpty(name_number4)){
			r.name_number4 = name_number4;
		}
		if (null != remind_open_close){
			r.remind_open_close = remind_open_close;
		}
		if (null != remind_low_power){
			r.remind_low_power = remind_low_power;
		}
		if (!StringUtil.isEmpty(whiteList)){
			if(whiteList.split(",").length < 0)renderFail("error_parameter_formate");
			r.whiteList = whiteList;
		}

		if (!StringUtil.isEmpty(production)){
			Production p = Production.find("p_name=?", production).first();
			if(p!=null)r.production = p;
		}
		if (mode != null){
			r.mode = mode;
		}
		r._save();
		JSONObject results = initResultJSON();
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
	public static void setRWatch_New(String sn, Long rId, String whiteList, String z) {
		RWatch r = null;
		if(!StringUtil.isEmpty(sn)){
			r = RWatch.find("bySerialNumber", sn).first();
		}
		if(rId != null){
			r = RWatch.find("id=?", rId).first();
			Session s = Session.find("bySessionID",z).first();
			if(s == null)renderFail("error_session_expired");
		}
		if(r == null){
			renderFail("error_rwatch_not_exist");
		}
		
		if (!StringUtil.isEmpty(whiteList)){
			String[] wl = whiteList.split(",");
			if(wl == null || wl.length == 0)renderFail("error_parameter_formate");
			for(String wll:wl){
				if(wll.length() <= 1)renderFail("error_parameter_formate");
				String[] dwl = wll.split(":");
				if(StringUtil.isEmpty(r.whiteList)){
					r.whiteList = dwl[1]+":"+dwl[0];
				}else if(r.whiteList.contains(dwl[1])){
					String[] rwl = r.whiteList.split(",");
					r.whiteList = "";
					for(String rwll:rwl){
						if(rwll.contains(dwl[1]))continue;
						else {
							if(StringUtil.isEmpty(r.whiteList))r.whiteList = rwll;
							else r.whiteList = r.whiteList+"," + rwll;
						}
					}
					if(StringUtil.isEmpty(r.whiteList)){
						r.whiteList = dwl[1]+":"+dwl[0];
					}else{
						r.whiteList = r.whiteList+","+dwl[1]+":"+dwl[0];
					}
				}else{
					r.whiteList = r.whiteList+","+dwl[1]+":"+dwl[0];
				}
			}
			r._save();
		}
		JSONObject results = initResultJSON();
		renderSuccess(results);
	}
	

	/**
	 * 删除白名单
	 * 
	 * @param sn
	 * @param rId
	 * @param whiteList
	 * @param z
	 */
	public static void delWhiteList(String sn, Long rId, @Required String whiteList, String z) {
		
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		
		RWatch r = null;
		if(!StringUtil.isEmpty(sn)){
			r = RWatch.find("bySerialNumber", sn).first();
		}
		if(rId != null){
			r = RWatch.find("id=?", rId).first();
			Session s = Session.find("bySessionID",z).first();
			if(s == null)renderFail("error_session_expired");
		}
		if(r == null){
			renderFail("error_rwatch_not_exist");
		}
		
		String[] wl = whiteList.split(",");
		if(wl == null || wl.length == 0)renderFail("error_parameter_formate");
		for(String wll:wl){
			if(wll.length() <= 1)renderFail("error_parameter_formate");
			String[] dwl = wll.split(":");
			if(StringUtil.isEmpty(r.whiteList)){
				break;
			}else if(r.whiteList.contains(dwl[1])){
				String[] rwl = r.whiteList.split(",");
				r.whiteList = "";
				for(String rwll:rwl){
					if(rwll.contains(dwl[1]))continue;
					else {
						if(StringUtil.isEmpty(r.whiteList))r.whiteList = rwll;
						else r.whiteList = r.whiteList+"," + rwll;
					}
				}
			}
		}
		r._save();
		JSONObject results = initResultJSON();
		renderSuccess(results);
	}
	
	/**
	 * 手表获取信息(定位器)
	 * 
	 * @param sn
	 */
	public static void getRWatchInfo(@Required String sn) {
		
		// 参数验证
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}

		List<RWatch> rwatchs = RWatch.find("bySerialNumber", sn).fetch();
		if(rwatchs.size() !=1 ){
			SN s = SN.find("bySn", sn).first();
			if(s != null){
				JSONObject results = initResultJSON();
				results.put("timestamp",new Date().getTime()+"");
				renderSuccess(results);
			}
			renderFail("error_rwatch_not_exist");
		}
		
		JSONObject results = initResultJSON();
		JSONObject data = initResultJSON();
		if (!rwatchs.isEmpty()) {
			for(RWatch r : rwatchs){
				data.put("rId", r.id);
				data.put("imei", r.imei);
				data.put("m_number", r.m_number);
				data.put("nickname", r.nickname);
				data.put("guardian_number1", r.guardian_number1);
				data.put("name_number1", r.name_number1);
				data.put("guardian_number2", r.guardian_number2);
				data.put("name_number2", r.name_number2);
				data.put("guardian_number3", r.guardian_number3);
				data.put("name_number3", r.name_number3);
				data.put("guardian_number4", r.guardian_number4);
				data.put("name_number4", r.name_number4);
				data.put("remind_low_power", r.remind_low_power);
				data.put("remind_open_close", r.remind_open_close);
				data.put("bindDate", DateUtil.reverseDate(r.bindDate,3));
				if(r.production != null){
					data.put("production", r.production.p_name);
				}
				if(r.mode == null || r.mode == 0){
					data.put("mode", "900");
				}else{
					data.put("mode", r.mode+"");
				}
				data.put("sn", r.serialNumber);
				if(r.whiteList != null){
					String[] wList = r.whiteList.split(",");
					if(wList.length > 0){
						JSONArray d = initResultJSONArray();
						for(String s : wList){
							JSONObject wl = initResultJSON();
							if(!s.equals(":")){
								String[] wll = s.split(":");
            					if(wll != null && wll.length!=2){
            						wl.put(wll[0], wll[1]);
            						d.add(wl);
            					}
                            }
						}
						data.put("whiteList", d);
					}
				}
				List<TimeInterval> tis = TimeInterval.find("rWatch=?", r).fetch();
				JSONArray datalist = initResultJSONArray();
				for (TimeInterval ti:tis) {
					JSONObject subdata = initResultJSON();
					subdata.put("on", ti.on+"");
					subdata.put("startTime", ti.startTime);
					subdata.put("endTime", ti.endTime);
					subdata.put("interval", ti.interval+"");
					subdata.put("type", ti.type+"");
					datalist.add(subdata);
				}
				data.put("time_interval", datalist);
			}
		}
		results.put("list", data);
		results.put("timestamp",new Date().getTime()+"");
		renderSuccess(results);
	}
	
	/**
	 * 手表获取信息(定位器)
	 * 
	 * @param sn
	 */
	public static void getRWatchInfo_New(@Required String sn) {
		
		// 参数验证
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}

		List<RWatch> rwatchs = RWatch.find("bySerialNumber", sn).fetch();
		if(rwatchs.size() !=1 ){
			SN s = SN.find("bySn", sn).first();
			if(s != null){
				JSONObject results = initResultJSON();
				results.put("timestamp",new Date().getTime()+"");
				renderSuccess(results);
			}
			renderFail("error_rwatch_not_exist");
		}
		
		JSONObject results = initResultJSON();
		JSONObject data = initResultJSON();
		if (!rwatchs.isEmpty()) {
			for(RWatch r : rwatchs){
				data.put("rId", r.id);
				data.put("imei", r.imei);
				data.put("m_number", r.m_number);
				data.put("nickname", r.nickname);
				data.put("guardian_number1", r.guardian_number1);
				data.put("name_number1", r.name_number1);
				data.put("guardian_number2", r.guardian_number2);
				data.put("name_number2", r.name_number2);
				data.put("guardian_number3", r.guardian_number3);
				data.put("name_number3", r.name_number3);
				data.put("guardian_number4", r.guardian_number4);
				data.put("name_number4", r.name_number4);
				data.put("bindDate", DateUtil.reverseDate(r.bindDate,3));
				if(r.production != null){
					data.put("production", r.production.p_name);
				}
				if(r.mode == null || r.mode == 0){
					data.put("mode", "180");
				}else{
					data.put("mode", r.mode+"");
				}
				data.put("sn", r.serialNumber);
				if(r.whiteList != null){
					String[] wList = r.whiteList.split(",");
					if(wList.length > 0){
						JSONArray d = initResultJSONArray();
						for(String s : wList){
							JSONObject wl = initResultJSON();
							String[] wll = s.split(":");
							if(wll.length==2){
								wl.put(wll[1], wll[0]);
								d.add(wl);
							}
						}
						data.put("whiteList", d);
					}
				}
				
			}
		}
		results.put("list", data);
		results.put("timestamp",new Date().getTime()+"");
		renderSuccess(results);
	}

	/**
	 * 获取当前用户绑定的所有儿童手表(定位器)
	 * 
	 * @param z
	 */
	public static void getBindRWatchList(@Required String z) {

		// ....
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}

		Session s = sessionCache.get();
		if(s == null){
			renderFail("error_session_expired");
		}

		Customer c = s.c;
		play.Logger.debug("test:%s;", c.m_number);
		List<RWatch> rwatchs = RWatch.find("c_id=? and guardian_number1=?", c.id, c.m_number).fetch();

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
				if(r.mode == null || r.mode == 0){
					data.put("mode", "180");
				}else{
					data.put("mode", r.mode+"");
				}

				if(r.production != null){
					data.put("production", r.production.p_name);
				}
				data.put("sn", r.serialNumber);
				List<ElectronicFence> lef = ElectronicFence.find("byRWatch", r).fetch();
				if(lef.size() > 0){
					lef = ElectronicFence.find("byRWatch", r).fetch();
					JSONArray arrayef = initResultJSONArray();
					for(ElectronicFence ef:lef){
						JSONObject subef = initResultJSON();
						subef.put("electronicFence_lat", ef.lat);
						subef.put("electronicFence_lon", ef.lon);
						subef.put("electronicFence_radius", ef.radius);
						subef.put("electronicFence_datetime", DateUtil.reverseDate(ef.dateTime,3));
						subef.put("electronicFence_status", ef.on);
						subef.put("electronicFence_num", ef.num);
						arrayef.add(subef);
					}
					data.put("electronicFence", arrayef);
				}
				datalist.add(data);
			}
		}
		results.put("list", datalist);
		renderSuccess(results);
	}

	/**
	 * ...............(...)
	 * 
	 * @param z
	 */
	public static void getRWatchList(@Required String z) {
		
		// ....
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
				if(r.mode == null || r.mode == 0){
					data.put("mode", "900");
				}else{
					data.put("mode", r.mode+"");
				}
				
				if(r.production != null){
					data.put("production", r.production.p_name);
				}
				data.put("sn", r.serialNumber);
				List<ElectronicFence> lef = ElectronicFence.find("byRWatch", r).fetch();
				if(lef.size() > 0){
					lef = ElectronicFence.find("byRWatch", r).fetch();
					JSONArray arrayef = initResultJSONArray();
					for(ElectronicFence ef:lef){
						JSONObject subef = initResultJSON();
						subef.put("electronicFence_lat", ef.lat);
						subef.put("electronicFence_lon", ef.lon);
						subef.put("electronicFence_radius", ef.radius);
						subef.put("electronicFence_datetime", DateUtil.reverseDate(ef.dateTime,3));
						subef.put("electronicFence_status", ef.on);
						subef.put("electronicFence_num", ef.num);
						arrayef.add(subef);
					}
					data.put("electronicFence", arrayef);
				}
				datalist.add(data);
			}
		}
		results.put("list", datalist);
		renderSuccess(results);
	}
	
	/**
	 * 手表同步时间(定位器)
	 * 
	 * @param sn
	 */
	public static void syncTime(String sn) {
//		List<RWatch> rwatchs = RWatch.find("bySerialNumber", sn).fetch();
//		if(rwatchs.size() !=1 )renderFail("error_rwatch_not_exist");		
		renderText(new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
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
		List<ElectronicFence> lef = null;
		String tmpStr = null;
		try {
			tmpStr = new String(Coder.decryptBASE64(datas));
			if(StringUtil.isEmpty(tmpStr))renderFail("error_parameter_required");
		} catch (Exception e) {
			e.printStackTrace();
			renderFail("error_parameter_required");
		}
		String[] tmpArray = tmpStr.split(",");
		int length = tmpArray.length==0?1:tmpArray.length;

		for(int j=0;j<length;j++){
			String[] dataArray = tmpArray[j].split("\\|");
			String sn = dataArray[1];
			if(r == null){
				r = RWatch.find("bySerialNumber", sn).first();
				if(r == null || !r.isPersistent())renderFail("error_parameter_required");
			}
			
			String rt = dataArray[2];
			if(p == null){
				p = Production.find("byP_name", rt).first();
				if(p == null || !p.isPersistent())renderFail("error_parameter_required");
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
			
			int cellid1 = Integer.parseInt(dataArray[13]);
			int lac1 = Integer.parseInt(dataArray[14]);
			String signal1 = dataArray[15];
			
			int cellid2 = Integer.parseInt(dataArray[16]);
			int lac2 = Integer.parseInt(dataArray[17]);
			String signal2 = dataArray[18];
			
			int cellid3 = Integer.parseInt(dataArray[19]);
			int lac3 = Integer.parseInt(dataArray[20]);
			String signal3 = dataArray[21];
			
			int cellid4 = Integer.parseInt(dataArray[22]);
			int lac4 = Integer.parseInt(dataArray[23]);
			String signal4 = dataArray[24];
			
			int cellid5 = Integer.parseInt(dataArray[25]);
			int lac5 = Integer.parseInt(dataArray[26]);
			String signal5 = dataArray[27];
			
			int cellid6 = Integer.parseInt(dataArray[28]);
			int lac6 = Integer.parseInt(dataArray[29]);
			String signal6 = dataArray[30];
			
			int ta  = Integer.parseInt(dataArray[31]);
			
			l.dateTime = datetime;
			l.latitudeFlag = latitudeFlag;
			l.longitudeFlag = longitudeFlag;
			l.speed = speed;
			l.direction = direction;
			l.status = status;

			double tmpLatitude;
			double tmpLonitude;
			
			if(BigDecimal.valueOf(latitude).compareTo(BigDecimal.valueOf(0)) == 0 && BigDecimal.valueOf(longitude).compareTo(BigDecimal.valueOf(0)) == 0){
				String path = "http://minigps.net/as?x="+Integer.toHexString(mcc)+"-"+Integer.toHexString(mnc)+"-"+
			Integer.toHexString(lac1)+"-"+Integer.toHexString(cellid1)+"-"+Integer.toHexString(Integer.parseInt(signal1))+"-"+
						Integer.toHexString(lac2)+"-"+Integer.toHexString(cellid2)+"-"+Integer.toHexString(Integer.parseInt(signal2))+"-"+
			Integer.toHexString(lac3)+"-"+Integer.toHexString(cellid3)+"-"+Integer.toHexString(Integer.parseInt(signal3))+"-"+
						Integer.toHexString(lac4)+"-"+Integer.toHexString(cellid4)+"-"+Integer.toHexString(Integer.parseInt(signal4))+"-"+
			Integer.toHexString(lac5)+"-"+Integer.toHexString(cellid5)+"-"+Integer.toHexString(Integer.parseInt(signal5))+"-"+
						Integer.toHexString(lac6)+"-"+Integer.toHexString(cellid6)+"-"+Integer.toHexString(Integer.parseInt(signal6))+
			"&p=1&mt=0&ta="+ta+"&needaddress=0";
				try {
					play.Logger.info("path="+path+" info:"+mcc+"-"+mnc+"-"+cellid1+"-"+lac1+"-"+signal1+"-"+cellid2+"-"+lac2+"-"+signal2+"-"+cellid3+"-"+lac3+"-"+signal3+
							"-"+cellid4+"-"+lac4+"-"+signal4+"-"+cellid5+"-"+lac5+"-"+signal5+"-"+cellid6+"-"+lac6+"-"+signal6);
					JSONObject json = HttpRequestSend.sendRequestGet(path);
					if(json.getInt("status") == 0){
						l.cell_latitude = json.getDouble("lat");
						l.cell_longitude = json.getDouble("lon");
					}
				} catch (Exception e) {
					play.Logger.info("BS error paht="+path+" info:"+mcc+"-"+mnc+"-"+cellid1+"-"+lac1+"-"+signal1+"-"+cellid2+"-"+lac2+"-"+signal2+"-"+cellid3+"-"+lac3+"-"+signal3+
							"-"+cellid4+"-"+lac4+"-"+signal4+"-"+cellid5+"-"+lac5+"-"+signal5+"-"+cellid6+"-"+lac6+"-"+signal6);
					e.printStackTrace();
				}
				l.longitude = 0;
				l.latitude = 0;
				tmpLatitude = l.cell_latitude;
				tmpLonitude = l.cell_longitude;
			}else{
				l.longitude = longitude;
				l.latitude = latitude;
				l.cell_longitude = 0;
				l.cell_latitude = 0;
				tmpLatitude = l.latitude;
				tmpLonitude = l.longitude;
			}
			l.ta = ta;
			l.receivedTime = new Date();
			
			if(lef == null){
				lef = ElectronicFence.find("byRWatch", r).fetch();
				for(ElectronicFence ef:lef){
					if(ef != null){
						if(ef.on == 1){
							if(new BigDecimal(StringUtil.distanceBetween(tmpLatitude, tmpLonitude, ef.lat, ef.lon)).compareTo(new BigDecimal(ef.radius)) > 0){
								l.valid = 2;
							}
						}
					}
					if(l.valid != 2)l.valid = 1;
				}
			}else{
				l.valid = 0;
			}
			l._save();
		}
		JSONObject results = initResultJSON();
		JSONObject data = initResultJSON();
	
				data.put("rId", r.id);
				data.put("imei", r.imei);
				data.put("m_number", r.m_number);
				data.put("nickname", r.nickname);
				data.put("guardian_number1", r.guardian_number1);
				data.put("name_number1", r.name_number1);
				data.put("guardian_number2", r.guardian_number2);
				data.put("name_number2", r.name_number2);
				data.put("guardian_number3", r.guardian_number3);
				data.put("name_number3", r.name_number3);
				data.put("guardian_number4", r.guardian_number4);
				data.put("name_number4", r.name_number4);
				data.put("bindDate", DateUtil.reverseDate(r.bindDate,3));
				if(r.production != null){
					data.put("production", r.production.p_name);
				}
				if(r.mode == null || r.mode == 0){
					data.put("mode", "900");
				}else{
					data.put("mode", r.mode+"");
				}
				data.put("sn", r.serialNumber);
				if(r.whiteList != null){
					String[] wList = r.whiteList.split(",");
					if(wList.length > 0){
						JSONArray d = initResultJSONArray();
						for(String s : wList){
							JSONObject wl = initResultJSON();
							if(!s.equals(":")){
							String[] wll = s.split(":");
							if(wll !=null && wll.length==2){
								wl.put(wll[0], wll[1]);
								d.add(wl);
							}
						}
						}
						data.put("whiteList", d);
					}
				}
				List<TimeInterval> tis = TimeInterval.find("rWatch=?", r).fetch();
				JSONArray datalist = initResultJSONArray();	
				for (TimeInterval ti:tis) {
					JSONObject t_data = initResultJSON();
					t_data.put("on", ti.on+"");
					t_data.put("startTime", ti.startTime);
					t_data.put("endTime", ti.endTime);
					t_data.put("interval", ti.interval+"");
					t_data.put("type", ti.type+"");
					datalist.add(t_data);
				}
				
				data.put("time_interval", datalist);
		results.put("list", data);
		results.put("timestamp",new Date().getTime()+"");
		renderSuccess(results);
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
		RWatch r = RWatch.find("id=? and c_id=?", rId, s.c.id).first();
		if(r == null){
			renderFail("error_rwatch_not_exist");
		}
		int begin = 0;
		if(num == null){
			num = 1;
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
				locations = Location.find("rwatch_id=? and (receivedTime>? and receivedTime<?) order by id desc", rId, startTimeDate, endTimeDate).fetch(begin, num);
			}else if(startTimeDate != null && endTimeDate == null){
				locations = Location.find("rwatch_id=? and receivedTime>?  order by id desc", rId, startTimeDate).fetch(begin, num);
			}else if(startTimeDate == null && endTimeDate != null){
				locations = Location.find("rwatch_id=? and receivedTime<?  order by id desc", rId, endTimeDate).fetch(begin, num);
			}else if(startTimeDate == null && endTimeDate == null){
				locations = Location.find("rwatch_id=?  order by id desc", rId).fetch(begin, num);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			play.Logger.info("getLocation: "+e.getMessage());
			renderFail("error_dateformat", error_dateformat);
		}
		
		JSONObject results = initResultJSON();
		JSONArray datalist = initResultJSONArray();
		
		if (!locations.isEmpty()) {
			for(Location l : locations){
				if(	((new BigDecimal(l.latitude).compareTo(new BigDecimal(0.0))!=0 && 
						new BigDecimal(l.longitude).compareTo(new BigDecimal(0.0))!=0) || 
						(new BigDecimal(l.cell_latitude).compareTo(new BigDecimal(0.0))!=0 && 
						new BigDecimal(l.cell_longitude).compareTo(new BigDecimal(0.0))!=0))){
					JSONObject data = initResultJSON();
					data.put("id", l.id);
					data.put("rwatchId", l.rwatch.id);
					data.put("dateTime", l.dateTime);
					data.put("latitude", l.latitude);
					data.put("longitude", l.longitude);
					data.put("cell_latitude", l.cell_latitude);
					data.put("cell_longitude", l.cell_longitude);;
					data.put("speed", l.speed);
					data.put("direction", l.direction);
					data.put("dateTime", l.dateTime);
					data.put("status", l.status);
					data.put("latitudeFlag", l.latitudeFlag);
					data.put("longitudeFlag", l.longitudeFlag);
					data.put("waring", l.valid);
					data.put("sDate", (l.receivedTime+"").replace(".0", ""));
					datalist.add(data);
				}
			}
		}
		results.put("list", datalist);
		renderSuccess(results);
	}
	
	/**
	 * 即时定位
	 * 
	 * @param rId
	 * @param z
	 */
	public static void getInstantPosition(@Required Long rId, @Required String z) {
		
		// 参数验证
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		
		Session s = sessionCache.get();
		if(s == null){
			renderFail("error_session_expired");
		}
		RWatch r = RWatch.find("id=? and c_id=?", rId, s.c.id).first();
		if(r == null){
			renderFail("error_rwatch_not_exist");
		}
		Long now = new Date().getTime();
		List<Location> locations = Location.find("rwatch_id=?  order by id desc", rId).fetch(10);
		
		JSONObject results = initResultJSON();
		for(Location l : locations){
			if(now - l.receivedTime.getTime() < 180000 && 
					((new BigDecimal(l.latitude).compareTo(new BigDecimal(0.0))!=0 && new BigDecimal(l.longitude).compareTo(new BigDecimal(0.0))!=0)
					|| (new BigDecimal(l.cell_latitude).compareTo(new BigDecimal(0.0))!=0 && new BigDecimal(l.cell_longitude).compareTo(new BigDecimal(0.0))!=0))){
				results.put("id", l.id);
				results.put("rwatchId", l.rwatch.id);
				results.put("dateTime", l.dateTime);
				results.put("latitude", l.latitude);
				results.put("longitude", l.longitude);
				results.put("cell_latitude", l.cell_latitude);
				results.put("cell_longitude", l.cell_longitude);;
				results.put("speed", l.speed);
				results.put("direction", l.direction);
				results.put("dateTime", l.dateTime);
				results.put("status", l.status);
				results.put("latitudeFlag", l.latitudeFlag);
				results.put("longitudeFlag", l.longitudeFlag);
				results.put("waring", l.valid);
				results.put("sDate", (l.receivedTime+"").replace(".0", ""));
				break;
			}
		}
		renderSuccess(results);
	}
	

	public static void setInterval(@Required String startTime,@Required String endTime,@Required Integer interval, Integer on, Integer type, @Required Long rId, @Required String z) {
		
		// ....
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		
		Session s = sessionCache.get();
		if(s == null){
			renderFail("error_session_expired");
		}
		if(startTime.length() != 4 || Integer.parseInt(startTime) > 2359 || Integer.parseInt(startTime) < 0){
			renderFail("error_starttime");
		}
		if(endTime.length() != 4 || Integer.parseInt(endTime) > 2359 || Integer.parseInt(endTime) < 0){
			renderFail("error_endtime");
		}
		if(interval < 1){
			renderFail("error_interval_zero");
		}
		RWatch r = RWatch.find("id=? and c_id=?", rId, s.c.id).first();
		if(r == null){
			renderFail("error_rwatch_not_exist");
		}
		if(type == null){
			Object t = JPA.em().createNativeQuery("select max(type) from time_interval where rWatch_id = "+rId).getSingleResult();
			if(t == null){
				type = 1;
			}else{
				type = Integer.parseInt(t.toString())+1;
			}
		}
		TimeInterval ti = TimeInterval.find("type=? and rWatch_id=?", type, r.id).first();
		
		if(ti == null){
			ti = new TimeInterval();
			ti.dateTime = new Date();
			ti.endTime = endTime;
			ti.startTime = startTime;
			ti.type = type;
			ti.interval = interval;
			if(on != null)ti.on = on;
			else ti.on = 1;
			ti.rWatch = r;
		}else{
			if(startTime != null)ti.startTime = startTime;
			if(endTime != null)ti.endTime = endTime;
			if(interval != null)ti.interval = interval;
			if(on != null)ti.on = on;
			if(type != null)ti.type = type;
		}
		ti._save();
		
		renderSuccess(initResultJSON());
	}
	
	public static void getInterval(@Required Long rId, @Required String z) {
		
		// ....
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		
		Session s = sessionCache.get();
		if(s == null){
			renderFail("error_session_expired");
		}
		RWatch r = RWatch.find("id=? and c_id=?", rId, s.c.id).first();
		if(r == null){
			renderFail("error_rwatch_not_exist");
		}
		List<TimeInterval> tis = TimeInterval.find("rWatch=?", r).fetch();
		
		JSONObject results = initResultJSON();
		JSONArray datalist = initResultJSONArray();
		results.put("rId",rId);
		for (TimeInterval ti:tis) {
			JSONObject data = initResultJSON();
			data.put("on", ti.on+"");
			data.put("startTime", ti.startTime);
			data.put("endTime", ti.endTime);
			data.put("interval", ti.interval+"");
			data.put("type", ti.type+"");
			datalist.add(data);
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
	public static void setElectronicFence(@Required Long rId, @Required Integer num, @Required Integer on, Double lon, Double lat, Double radius, @Required String z) {
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		JSONObject results = initResultJSON();
		RWatch rWatch = RWatch.find("id=?",rId).first();
		if(rWatch == null)renderFail("error_rwatch_not_exist");
		if(num > 4)renderFail("error_parameter_required");
		ElectronicFence ef = ElectronicFence.find("rWatch_id=? and num=?", rWatch,num).first();
		if (ef == null) {
			ef = new ElectronicFence();
			ef.rWatch = rWatch;
		}
		ef.dateTime = new Date();
		ef.on = on;
		if (on == 1) {
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
		ef.num = num;
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
		RWatch r = RWatch.find("byM_number", w_number).first();
		if(r!=null && r.c != null)renderFail("error_rwatch_bind_full");
		
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
		rWatch._delete();
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

//		try {
//			boolean s = SendSMS.sendMsg(new String[]{w_number}, Play.configuration.getProperty("binding.path")+"?sn="+sn+"&m_number="+m_number+"&w_number="+w_number);
//			if(!s){
//				play.Logger.error("webBindingWatch: result="+s+" PNumber="+m_number+" sn="+sn);
//				renderText("............");
//			}
//		} catch (Exception e) {
//			play.Logger.error("webBindingWatch: PNumber="+m_number+" sn="+sn);
//			play.Logger.error(e.getMessage());
//			renderFail("error_unknown_command");
//		}
		
		addRWatch(m_number, w_number, sn);
		
		renderSuccess(results);
	}
	
	public static void insertSN(@Required String username, @Required String pwd, @Required String from,@Required String to) {
		if (Validation.hasErrors()) {
			renderText("error");
		}
		
		AdminManagement user = AdminManagement.find("byName", username).first();
		if(user == null)renderText("error username");
		if(!user.pwd.equals(pwd))renderText("error password");
		
		Integer snf = Integer.parseInt(from.substring(from.length()-5,from.length()));
		Integer snt = Integer.parseInt(to.substring(to.length()-5,to.length()));
		String str = from.substring(0,from.length()-5);
		
		Connection con = DB.getConnection();
		int count=0;
		try {
			con.setAutoCommit(false);
			con.setTransactionIsolation(con.TRANSACTION_READ_UNCOMMITTED);
			PreparedStatement pst = (PreparedStatement) con.prepareStatement("insert into serial_number(sn) values (?)");
			String tmp = "";
			for(long i=snf;i<=snt;i++){
				if(i<10)tmp="0000"+i;
				else if(i<100)tmp="000"+i;
				else if(i<1000)tmp="00"+i;
				else if(i<10000)tmp="0"+i;
				else if(i<100000)tmp=""+i;
				pst.setString(1, str+tmp);
				pst.addBatch();
				count++;
			}
			pst.executeBatch();
			con.commit();
			renderText(count);
		}catch(Exception e){
			play.Logger.error("insertSN:"+e.getMessage());
			renderText("error2");
		}finally {
			DB.close();
		}
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
