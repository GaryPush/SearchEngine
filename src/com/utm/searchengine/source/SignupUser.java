package com.utm.searchengine.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupUser {
	
	public static String handleSignUp(String[] args, Database db) throws ClassNotFoundException, SQLException {
		if (args[0].equals("") || args[4].equals("")) {
    		return "Please enter a username and a password";
    	}
		if (args[7].equals("") || args[8].equals("")) {
    		return "Please enter a security question and its answer.";
    	}
    	if(args[4].equals(args[5])){
    		//Passwords match
    		if(args[4].length() < 6){
    			return "Password must be at least 6 characters long.";
    		}
    		System.out.println("Passwords match");
    		String id;
    		if(args[6].equals("student")){
    			id = "s";
    		} else {
    			id = "i";
    		}
    		Connection conn = db.getConnection();
    		String query;
    		// Check if username already exists.
            query="Select * from SearchProject.account where user_name=?";
            ResultSet rs = db.handleQuery(query, new String[]{args[0]}, conn, false);
            if (rs.isBeforeFirst()){
              return "User name already exists.";
            }
            db.mystmt.close();
    		
            query="INSERT INTO SearchProject.account(user_name,pass,first_name,last_name,email,identity,question,answer) VALUES(?,?,?,?,?,?,?,?)";
            db.handleQuery(query, 
            		new String[]{args[0], args[4], args[1], args[2], args[3], id, args[7], args[8]}, conn, true);
            db.mystmt.close();
    	}else{
    		//PASSWORDS DON'T MATCH
    		return "Passwords not matching.";
    	}
    	return "";
	}

}
