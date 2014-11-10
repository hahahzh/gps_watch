package controllers;

import models.AdminManagement;


public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {

		if(AdminManagement.count() == 0){
			session.put("admin_name", "admin");
			return "admin".equals(username)&&"admin".equals(password);
		}
    	//初始化状态
		AdminManagement user = AdminManagement.find("byName", username).first();
		if(user == null){
			return false;
		}
		boolean flag = user != null && user.pwd.equals(password);
		if(flag){
			session.put("admin_id", user.id);
			session.put("admin_name", user.name);
			session.put("admin_role", user.role);
		}
		return flag;
    	}

    static void onDisconnected() {
        render();
    }

    static void onAuthenticated() {
    	render();
    }
}