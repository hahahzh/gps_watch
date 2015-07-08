package controllers;

import models.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import play.Logger;
import play.Play;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Before;
import play.mvc.Controller;
import utils.DateUtil;
import utils.JSONUtil;
import utils.SendSMS;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Application extends Controller {

    /**
     * 【这个方法是执行其他接口时，先要通过的方法，相当于一个拦截器，通常用于验证用户权限等先决事件。】
     * 校验Session是否过期
     *
     * @param z
     */
    @Before(unless = {"index", "register", "login", "watchNodistList"}, priority = 1)
    public static void validateSessionID(@Required String z) {
        Session s = Session.find("bySessionID", z).first();
        Logger.debug("interface-z:%s;", s.c.m_number);
        Master.sessionCache.set(s);
        if (s == null) {
            Master.renderFail("error_session_expired");
        }
    }

    public static void index() {
        String t = Play.ctxPath;
        String sn = "sn";
        String m_number = "m_number";
        String w_number = "w_number";
        String s = Play.configuration.getProperty("binding.path")+"?sn="+sn+"&m_number="+m_number+"&w_number="+w_number;
        render(t, s);
    }

    public static void register(@Required String uname, @Required Integer digit, @Required String pass, @Required String email, @Required String city) {
        if (Validation.hasErrors()) {
            Master.renderFail("error_parameter_required");
        }
        CheckDigit c = CheckDigit.find("d=?", digit).first();
        // 【不存在，不通过】
        if (c == null) {
            Master.renderFail("error_checkdigit");
        }
        if (!c.m.equals(uname)) {
            Master.renderFail("error_checkdigit");
        }
        if (new Date().getTime() - c.updatetime > 1800000) {
            c.delete();
            Master.renderFail("error_checkdigit");
        }

        Customer m = Customer.find("byM_number", uname.trim()).first();
        if (m != null) {
            play.Logger.info("register:error_username_already_used");
            Master.renderFail("error_username_already_used");
        }

        m = new Customer();
        m.os = 0; // register from web.
        m.m_number = uname;
        m.nickname = uname;
        m.email = email;
        m.pwd = pass;
        m.city = city;
        m.updatetime = new Date();
        m.save();

        // 【注册完了，删除那条验证记录。】
        c.delete();

        JSONObject results = JSONUtil.getNewJSON();
        play.Logger.info("web register:OK " + m.m_number + " " + m.city);
        Master.renderSuccess(results);
    }

    public static void login(@Required String uname, @Required String pass) {
        if (Validation.hasErrors()) {
            Master.renderFail("error_parameter_required");
        }

        if (uname == null || uname.isEmpty()) {
            Master.renderFail("error_parameter_required");
        }
        Customer c = Customer.find("byM_number", uname).first();

        if (c == null) {
            c = Customer.find("byEmail", uname).first();
            // if(c == null)c = Customer.find("byWeixin", phone).first();
        }

        if (c == null || !c.pwd.equals(pass)) {
            Master.renderFail("error_username_or_password_not_match");
        }

        Session s = Session.find("byC", c).first();
        if (s == null) {
            s = new Session();
            s.c = c;
            s.sessionID = UUID.randomUUID().toString();
            s.nickname = c.nickname;
        }
        s.loginUpdatetime = new Date();
        s._save();

        c.updatetime = new Date();
        c._save();
        Master.sessionCache.set(s);
        JSONObject results = Master.initResultJSON();
        results.put("uid", c.getId());
        results.put("phone", c.m_number);
        results.put("name", c.nickname);
        results.put("city", c.city);
        results.put("session", s.sessionID);
        play.Logger.info("login:OK " + c.nickname + " " + c.updatetime + "|" + c.m_number + " " + c.mac);
        Master.renderSuccess(results);
    }

    public static void getBindRWatch(@Required String m_number) {
        // Session s = Master.sessionCache.get();
        RWatch rwatch = RWatch.find("guardian_number1=?", m_number).first();
        JSONObject results = Master.initResultJSON();
        results.put("m_number", rwatch);
        Master.renderSuccess(results);
    }

    /**
     * 获取电子围栏列表
     * need @Required String z
     */
    public static void getElectronicFenceList(@Required Long rId) {
        if (Validation.hasErrors()) {
            Master.renderFail("error_parameter_required");
        }
        RWatch rwatch = RWatch.find("id=?", rId).first();
        Logger.debug("RWatch:%s,%s;", rwatch.id, rwatch.m_number);
        JSONObject results = Master.initResultJSON();
        JSONArray datalist = Master.initResultJSONArray();
        List<ElectronicFence> efs = ElectronicFence.find("byRWatch", rwatch).fetch();
        for (ElectronicFence ef : efs) {
            JSONObject data = Master.initResultJSON();
            data.put("id", ef.id);
            data.put("rid", ef.rWatch.id);
            data.put("on", ef.on);
            data.put("lat", ef.lat);
            data.put("lon", ef.lon);
            data.put("radius", ef.radius);
            data.put("dateTime", DateUtil.reverseDate(ef.dateTime, 3));
            data.put("num", ef.num);
            datalist.add(data);
        }
        results.put("list", datalist);
        Master.renderSuccess(results);
    }

    /**
     * 手表获取免打扰信息接口
     * @param sn
     */
    public static void watchNodistList(String sn) {
        if (Validation.hasErrors()) {
            Master.renderFail("error_parameter_required");
        }

        RWatch rwatch = RWatch.find("bySerialNumber", sn).first();
        JSONObject results = Master.initResultJSON();
        JSONArray datalist = Master.initResultJSONArray();
        if (rwatch == null) Master.renderFail("error_RWatch_not_exist");
        List<NoDisturbing> nds = NoDisturbing.find("RWatch=?", rwatch).fetch();
        for (NoDisturbing nd : nds) {
            JSONObject data = Master.initResultJSON();
            data.put("id", nd.id);
            data.put("rid", nd.rWatch.id);
            data.put("num", nd.num);
            data.put("mon", nd.monday);
            data.put("tue", nd.tuesday);
            data.put("wen", nd.wensday);
            data.put("thu", nd.thusday);
            data.put("fri", nd.friday);
            data.put("sat", nd.satuday);
            data.put("sun", nd.sunday);
            data.put("from", nd.fromTime);
            data.put("to", nd.toTime);
            datalist.add(data);
        }
        results.put("list", datalist);
        Master.renderSuccess(results);
    }

    /**
     * 获取免打扰列表的接口
     */
    public static void getNodistList() {
        if (Validation.hasErrors()) {
            Master.renderFail("error_parameter_required");
        }
        Session s = Master.sessionCache.get();
        if (s == null) {
            Master.renderFail("error_session_expired");
        }

        RWatch rwatch = RWatch.find("guardian_number1=?", s.c.m_number).first();
        JSONObject results = Master.initResultJSON();
        JSONArray datalist = Master.initResultJSONArray();
        if (rwatch == null) Master.renderFail("error_RWatch_not_exist");
        List<NoDisturbing> nds = NoDisturbing.find("RWatch=?", rwatch).fetch();
        for (NoDisturbing nd : nds) {
            JSONObject data = Master.initResultJSON();
            data.put("id", nd.id);
            data.put("rid", nd.rWatch.id);
            data.put("num", nd.num);
            data.put("mon", nd.monday);
            data.put("tue", nd.tuesday);
            data.put("wen", nd.wensday);
            data.put("thu", nd.thusday);
            data.put("fri", nd.friday);
            data.put("sat", nd.satuday);
            data.put("sun", nd.sunday);
            data.put("from", nd.fromTime);
            data.put("to", nd.toTime);
            datalist.add(data);
        }
        results.put("list", datalist);
        Master.renderSuccess(results);
    }

    /**
     * 设置白名单通讯簿的功能
     * @param id 传值为null 时，则表示添加，否则表示修改相应记录
     * @param phone
     * @param name
     */
    public static void setWhitePhone(Long id, @Required String phone, @Required String name) {
        if (Validation.hasErrors()) {
            Master.renderFail("error_parameter_required");
        }
        Session s = Master.sessionCache.get();
        if (s == null) {
            Master.renderFail("error_session_expired");
        }

        RWatch rwatch = RWatch.find("guardian_number1=?", s.c.m_number).first();

        WhitePhone wp = null;
        if (null == id) {
            wp = new WhitePhone();
        } else {
            wp = WhitePhone.findById(id);
        }
        wp.m_number = phone;
        wp.name = name;
        wp.rWatch = rwatch;
        wp.save();
        JSONObject results = Master.initResultJSON();
        Master.renderSuccess(results);
    }

    /**
     * 获取白名单通讯簿的接口
     */
    public static void getWhitePhoneList() {
        if (Validation.hasErrors()) {
            Master.renderFail("error_parameter_required");
        }
        Session s = Master.sessionCache.get();
        if (s == null) {
            Master.renderFail("error_session_expired");
        }

        RWatch rwatch = RWatch.find("guardian_number1=?", s.c.m_number).first();
        if (rwatch == null) Master.renderFail("error_RWatch_not_exist");

        JSONObject results = Master.initResultJSON();
        JSONArray datalist = Master.initResultJSONArray();
        List<WhitePhone> wps = WhitePhone.find("RWatch=?", rwatch).fetch();
        for (WhitePhone wp : wps) {
            JSONObject data = Master.initResultJSON();
            data.put("id", wp.id);
            data.put("rid", wp.rWatch.id);
            data.put("m_number", wp.m_number);
            data.put("name", wp.name);
            datalist.add(data);
        }
        results.put("list", datalist);
        Master.renderSuccess(results);
    }

    /**
     * 删除白名单通讯簿中某一项。
     * @param id
     */
    public static void delWhitePhone(Long id) {
        if (Validation.hasErrors()) {
            Master.renderFail("error_parameter_required");
        }
        Session s = Master.sessionCache.get();
        if (s == null) {
            Master.renderFail("error_session_expired");
        }

        RWatch rwatch = RWatch.find("guardian_number1=?", s.c.m_number).first();
        // make sure the white phone is related with current r_watch.
        WhitePhone.delete("id=? and RWatch=?", id, rwatch);
        JSONObject results = Master.initResultJSON();
        Master.renderSuccess(results);
    }

    /**
     * 设置免打扰的接口
     *
     * @param num
     * @param mon
     * @param tue
     * @param wen
     * @param thu
     * @param fri
     * @param sat
     * @param sun
     * @param from
     * @param to
     */
    public static void setNodist(@Required Integer num, @Required Boolean mon, @Required Boolean tue,
                                 @Required Boolean wen, @Required Boolean thu, @Required Boolean fri,
                                 @Required Boolean sat, @Required Boolean sun, @Required String from,
                                 @Required String to) {
        if (Validation.hasErrors()) {
            Master.renderFail("error_parameter_required");
        }
        Session s = Master.sessionCache.get();
        if (s == null) {
            Master.renderFail("error_session_expired");
        }

        RWatch rwatch = RWatch.find("guardian_number1=?", s.c.m_number).first();

        NoDisturbing nd = NoDisturbing.find("RWatch=? and num=?", rwatch, num).first();
        if (null == nd) {
            nd = new NoDisturbing();
            nd.rWatch = rwatch;
            nd.num = num;
        }
        nd.monday = mon;
        nd.tuesday = tue;
        nd.wensday = wen;
        nd.thusday = thu;
        nd.friday = fri;
        nd.satuday = sat;
        nd.sunday = sun;
        nd.fromTime = from;
        nd.toTime = to;
        nd.save();
        JSONObject results = Master.initResultJSON();
        Master.renderSuccess(results);
    }

    public static void shutdown() {
        if (Validation.hasErrors()) {
            Master.renderFail("error_parameter_required");
        }
        Session s = Master.sessionCache.get();
        if (s == null) {
            Master.renderFail("error_session_expired");
        }

        RWatch rwatch = RWatch.find("guardian_number1=?", s.c.m_number).first();
        try {
            boolean b = SendSMS.sendMsg(new String[]{rwatch.m_number}, "OVIGJ");
            JSONObject results = Master.initResultJSON();
            Master.renderSuccess(results);
        } catch (Exception e) {
            // e.printStackTrace();
            play.Logger.error("shutdown error: RWatch=" + rwatch.m_number + ";");
            play.Logger.error(e.getMessage());
            Master.renderFail("系统繁忙关机失败请稍后再试。");
        }
    }
}