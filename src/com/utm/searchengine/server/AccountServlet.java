package com.utm.searchengine.server;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.UploadOptions;
import com.utm.searchengine.source.Account;
import com.utm.searchengine.source.Database;
import com.utm.searchengine.source.ServeSessionID;

public class AccountServlet extends HttpServlet{
	
	private String accountEditUrl = "";
	private Database db = new Database();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		String currentUser = ServeSessionID.getSessionID(req, "username");
		  accountEditUrl = "/accountEdit?user=" + currentUser;
		  String owner = req.getParameter("user");
	      	if (currentUser == null){
	  		  res.sendRedirect("/login");
	  		  return;
	  	    }
		  if (owner == null) {
			  res.sendRedirect("/account?user=" + currentUser);
			  return;
		  }
		  String info = "";
		  if (currentUser.equals(owner) || currentUser.equals("admin")){
			  // owner
			  try {
				info = Account.createAccountInfoAsOwner(owner, db);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  } else {
			  // guest
			  try {
				info = Account.createAccountInfoAsGuest(owner, db);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  req.setAttribute("accountInfo", info);
		  req.getRequestDispatcher("/WEB-INF/account.jsp").forward(req, res);
	}
}
