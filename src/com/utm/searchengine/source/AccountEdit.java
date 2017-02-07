package com.utm.searchengine.source;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountEdit {
	
	public static String handleEdit(String[] args, Database db) throws ClassNotFoundException, SQLException {
		Connection conn = db.getConnection();
        String query = "Update SearchProject.account Set first_name=?, last_name=?, email=?, picture=? WHERE user_name=?";
        db.handleQuery(query, args, conn, true);
        return "";
	}

}
