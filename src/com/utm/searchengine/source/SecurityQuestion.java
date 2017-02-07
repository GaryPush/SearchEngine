package com.utm.searchengine.source;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecurityQuestion {
	
	public static Boolean handleAnswer(String username, String answer, Database db) 
			throws SQLException, ClassNotFoundException {
		Connection conn = db.getConnection();
		String query = "Select * from SearchProject.account where user_name=? and answer=?";
        ResultSet rs = db.handleQuery(query, new String[]{username, answer}, conn, false);
        return rs.next();
	}

}
