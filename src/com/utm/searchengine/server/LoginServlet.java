package com.utm.searchengine.server;

import java.io.IOException;
import com.utm.searchengine.source.*;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mortbay.log.Log;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.utils.SystemProperty;
import com.utm.searchengine.source.LoginUser;
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet{
	Database db = new Database();
    @Override
	public void doPost(HttpServletRequest request,
	                    HttpServletResponse response)
	            throws ServletException, IOException
	  {
		// Set response content type
	      String[] output = new String[7];
	      output[0] = request.getParameter("user");
	      output[1] = request.getParameter("password");
	      System.out.println(output[0]);
	      System.out.println(output[1]);
	      try {
			String errMsg = LoginUser.handleLogin(output, db);
			if(errMsg.equals("")){
				HttpSession session=request.getSession();
			    String userName = output[0];
				session.setAttribute("username", userName);
			    response.sendRedirect("/search");
			}else{
				request.setAttribute("error", errMsg);
				request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in login" + e);
			e.printStackTrace();
		}
	  }
	  
	  // Method to handle POST method request.
	  public void doGet(HttpServletRequest req,
	                     HttpServletResponse resp)
	      throws ServletException, IOException {
		  HttpSession session=req.getSession();
		  session.setAttribute("username", null);
		  req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
		  //doPost(req,resp);
	  
	  }
}