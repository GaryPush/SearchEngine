package com.utm.searchengine.source;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
	
	private static String getAccountInfo(String owner, Database db) throws ClassNotFoundException, SQLException {
		String output = "";
		Connection conn = db.getConnection();
		String query;
		String id;
		// Check if username already exists.
        query="Select * from SearchProject.account where user_name=?";
        ResultSet rs = db.handleQuery(query, new String[]{owner}, conn, false);
        if(rs.next() == false){
    		return "Incorrect username or password.";
    	}
        if (rs.getString("identity").equals("i")){
        	id = "Instructor";
        } else {
        	id = "Student";
        }
        String name = rs.getString("first_name") + " " + rs.getString("last_name");
        String email = rs.getString("email");
        if (name.equals(" ")) {
        	name = "N/A";
        }
        if (email.equals("")) {
        	email = "N/A";
        }
        output += "<img src=\"" + rs.getString("picture") + "\" alt=\"Photo\" style=\"width:304px;height:228px;\"><br>"
        		+ "Full Name: " + name + "<br>"
        		+ "Identity: " + id + "<br>"
				+ "Email: " + email + "<br>";
        return output;
	}
	
	public static String createAccountInfoAsOwner(String owner, Database db) throws ClassNotFoundException, SQLException {
		String editButton = "<a name=\"edit\" href=\"/accountEdit?user=" + owner
	        			+ "\">Edit</a><br>";
		return getAccountInfo(owner, db) + editButton;
	}
	
	public static String createAccountInfoAsGuest(String owner, Database db) throws ClassNotFoundException, SQLException {
		return getAccountInfo(owner, db);
	}

}
