package com.utm.searchengine.source;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfirmUser {
	
	public static String handleConfirm(String username, Database db) 
			throws ClassNotFoundException, SQLException {
		Connection conn = db.getConnection();
		String query=  "Select * from SearchProject.account where user_name=?";
        ResultSet rs = db.handleQuery(query, new String[]{username}, conn, false);
        if(rs.next() == false){
    		return "error";
    	}
		return rs.getString("question");
	}

}
