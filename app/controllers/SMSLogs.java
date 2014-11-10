package controllers;


import java.lang.reflect.Constructor;
import java.util.Date;

import models.SMSLog;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;
import utils.DateUtil;
import utils.SendSMS;
import utils.StringUtil;

@Check("admin")
@With(Secure.class)
public class SMSLogs extends CRUD {
	
	public static void create() throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        SMSLog object = new SMSLog();
        object.content = params.get("content");
        String mobiles = "";
        String wMobile = params.get("wMobile").trim();
        String[] phoneNos;
        if(StringUtil.isEmpty(wMobile)){
        	phoneNos = params.getAll("selMobile");
        	for(String tmp : phoneNos){
            	mobiles += tmp+",";
            }
        }else{
        	phoneNos = wMobile.split(",");
        	mobiles = wMobile.trim();
        }
        
        object.phoneNos = mobiles;
        validation.valid(object);
        
        boolean flag = SendSMS.sendMsg(phoneNos, object.content);
        
        object.updatetime = DateUtil.reverseDate(new Date(), 0);
        if (validation.hasErrors()) {
            renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
            try {
                render(request.controller.replace(".", "/") + "/blank.html", type, object);
            } catch (TemplateNotFoundException e) {
                render("CRUD/blank.html", type, object);
            }
        }
        object._save();
        flash.success(play.i18n.Messages.get("crud.created", type.modelName));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        if (params.get("_saveAndAddAnother") != null) {
            redirect(request.controller + ".blank");
        }
        redirect(request.controller + ".show", object._key());
    }
	
}