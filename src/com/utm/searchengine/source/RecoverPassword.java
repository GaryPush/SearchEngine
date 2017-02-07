package com.utm.searchengine.source;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecoverPassword {
	
	public static void handleRecovery(String username, String password, Database db) 
			throws ClassNotFoundException, SQLException {
		Connection conn = db.getConnection();
        String query = "Update SearchProject.account Set pass=? WHERE user_name=?";
        db.handleQuery(query, new String[]{password, username}, conn, true);
	}

}
