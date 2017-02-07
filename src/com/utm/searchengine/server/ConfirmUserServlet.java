package com.utm.searchengine.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.FileInfo;
import com.google.appengine.api.blobstore.UploadOptions;
import com.google.appengine.api.utils.SystemProperty;
import com.utm.searchengine.source.AccountEdit;
import com.utm.searchengine.source.ConfirmUser;
import com.utm.searchengine.source.Database;
import com.utm.searchengine.source.ServeSessionID;
import com.utm.searchengine.source.ServeUrl;
import com.utm.searchengine.source.UploadFile;

public class ConfirmUserServlet extends HttpServlet{

	private Database db = new Database();
    
	@Override
	  public void doPost(HttpServletRequest request,
	                    HttpServletResponse response)
	            throws ServletException, IOException
	  {
		  System.out.println("confirmuser doPost");
	      String username = request.getParameter("user");
	      
	      if (username.equals("")) {
	    	  dispatchForm(request, response, "Please enter your username.");
	    	  return;
	      }
	      String question = "";
		try {
			question = ConfirmUser.handleConfirm(username, db);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (question.equals("error")) {
			dispatchForm(request, response, "Username does not exist.");
			return;
		}
		response.sendRedirect("/securityQuestion?user=" + username + "&question=" + question);
	      
	  }
		
	  
	  public void doGet(HttpServletRequest req,
	                     HttpServletResponse resp)
	      throws ServletException, IOException {
		 
		  dispatchForm(req, resp, null);
	  }
	  
	  protected void dispatchForm(
	            HttpServletRequest req,
	            HttpServletResponse resp, 
	            String msg
	    ) throws ServletException, IOException {
	        if (msg != null) {
	            req.setAttribute("error", msg);
	        }
	        req.getRequestDispatcher("/WEB-INF/confirmUser.jsp").forward(req, resp);
	        
	    }
}