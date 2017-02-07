package com.utm.searchengine.source;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginUser {
	
	public static String handleLogin(String[] args, Database db) throws ClassNotFoundException, SQLException {
		if (args[0].equals("") || args[1].equals("")){
    		return "Please enter your username and password";
    	}
		Connection conn = db.getConnection();
		String query;
		// Check if username already exists.
        query="Select * from SearchProject.account where user_name=? and pass=?";
        System.out.println("Login success1");
        ResultSet rs = db.handleQuery(query, new String[]{args[0], args[1]}, conn, false);
        System.out.println("Login success2");
        if(rs.next() == false){
    		return "Incorrect username or password.";
    	}
        System.out.println("Login success3");
        return "";
	}

}
