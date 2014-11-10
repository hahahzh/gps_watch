package controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.RWatch;
import controllers.CRUD.ObjectType;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;
import utils.DateUtil;

@Check("admin")
@With(Secure.class)
public class Sessions extends CRUD {
	
    public static void list(int page, String search, String searchFields, String orderBy, String order) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        if(orderBy == null && orderBy == null){
        	orderBy = "loginUpdatetime";
        	order = "DESC";
        }
        List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, (String) request.args.get("where"));
        Long count = type.count(search, searchFields, (String) request.args.get("where"));
        Long totalCount = type.count(null, null, (String) request.args.get("where"));
        try {
            render(type, objects, count, totalCount, page, orderBy, order);
        } catch (TemplateNotFoundException e) {
            render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order);
        }
    }
}