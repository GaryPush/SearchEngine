package com.utm.searchengine.server;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utm.searchengine.source.Database;
import com.utm.searchengine.source.RecoverPassword;
import com.utm.searchengine.source.SecurityQuestion;

public class RecoverPasswordServlet extends HttpServlet{
	
	private Database db = new Database();
	private String user = "";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		  user = req.getParameter("user");
		  if (user == null || !(boolean) req.getSession().getAttribute("recover")) {
			  res.sendRedirect("/confirmUser");
			  return;
		  }
		  
		  req.getRequestDispatcher("/WEB-INF/recoverPassword.jsp").forward(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req,
	                    HttpServletResponse resp)
	            throws ServletException, IOException
	{
		String p1 = req.getParameter("p1");
		String p2 = req.getParameter("p2");
		if (p1.equals("") || p2.equals("")) {
			req.setAttribute("error", "Please enter your new password.");
			req.getRequestDispatcher("/WEB-INF/recoverPassword.jsp").forward(req, resp);
			return;
		}
		if (!p1.equals(p2)) {
			req.setAttribute("error", "Passwords not match.");
			req.getRequestDispatcher("/WEB-INF/recoverPassword.jsp").forward(req, resp);
			return;
		}
		if (p1.length() < 6) {
			req.setAttribute("error", "Password must be at least 6 characters long.");
			req.getRequestDispatcher("/WEB-INF/recoverPassword.jsp").forward(req, resp);
			return;
		}
		try {
			if ((boolean) req.getSession().getAttribute("recover")) {
				RecoverPassword.handleRecovery(user, p1, db);
				req.getSession().setAttribute("recover", false);
				resp.sendRedirect("/login");
				return;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.sendRedirect("/confirmUser");
		return;
	}
}
