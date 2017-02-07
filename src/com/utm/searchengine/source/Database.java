package com.utm.searchengine.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.appengine.api.utils.SystemProperty;

public class Database {
	
   public PreparedStatement mystmt;
	
  public Connection getConnection() throws SQLException, ClassNotFoundException{ //throws SQLException, ClassNotFoundException, IOException   {
    String url = null;

    if (SystemProperty.environment.value() ==
        SystemProperty.Environment.Value.Production) {
      // Connecting from App Engine.
      // Load the class that provides the "jdbc:google:mysql://"
      // prefix.
      Class.forName("com.mysql.jdbc.GoogleDriver");
      url =
        "jdbc:google:mysql://search-1239:search301?user=root&password=pass";
    } else {
     // Connecting from an external network.
      Class.forName("com.mysql.jdbc.Driver");
      url = "jdbc:mysql://173.194.254.104:3306?user=sample&password=pass";
    }

    
    return DriverManager.getConnection(url);
    
  }
  
  public ResultSet handleQuery(String query, String[] args, Connection conn, Boolean insert){
	  try {
      mystmt = conn.prepareStatement(query);
      for (int i = 0; i < args.length; i++){
        mystmt.setString(i+1, args[i]);
      }
      if (insert) {
    	  mystmt.executeUpdate();
    	  mystmt.close();
    	  return null;
      }
      ResultSet rs =  mystmt.executeQuery();
      return rs;
    } catch (SQLException e) {
      // TODO Auto-generated catch block
    	System.out.println(e);
      e.printStackTrace();
      return null;
    }
  }

}
