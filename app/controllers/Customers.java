package controllers;


import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Customer;
import models.RWatch;
import play.db.Model;
import play.db.jpa.JPA;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;
import utils.DateUtil;
import utils.ExportExcel;

@Check("admin")
@With(Secure.class)
public class Customers extends CRUD {
	
	  public static void list(int page, String search, String searchFields, String orderBy, String order, String startDate, String endDate) {
	        ObjectType type = ObjectType.get(getControllerClass());
	        notFoundIfNull(type);
	        if (page < 1) {
	            page = 1;
	        }

	        if((startDate == null && endDate == null) || ("".equals(startDate) && "".equals(endDate))){
	        	Date sDate = DateUtil.addDayOfMonth(new Date(),-7,true);
	        	Date eDate = new Date();
	        	startDate = DateUtil.reverseDate(sDate, 4);
	        	endDate = DateUtil.reverseDate(eDate, 4);
	        }
    		String where = "updatetime>'"+startDate+":00' and updatetime<'"+endDate+":00'";
	        List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, where);
	        List<RWatch> rwatchs = new ArrayList<RWatch>();
	        for(Model m:objects){
	        	RWatch r = RWatch.find("c_id=?", m._key()).first();
	        	if(r == null){
	        		rwatchs.add(null);
	        	}else{
	        		rwatchs.add(r);
	        	}
	        }
	        
	        Long count = type.count(search, searchFields, where);
	        Long totalCount = type.count(null, null, where);
	        try {
	            render(type, objects, rwatchs, count, totalCount, page, orderBy, order, startDate, endDate);
	        } catch (TemplateNotFoundException e) {
	            render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order, startDate, endDate);
	        }
	  }
    
    public static List<Customer> getCustomers(){
    	//return JPA.em().createNativeQuery("select m_number,nickname from customer").getResultList();
    	return Customer.findAll();
    }
}