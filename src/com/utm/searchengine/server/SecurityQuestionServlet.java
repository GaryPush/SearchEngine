package com.utm.searchengine.server;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.utm.searchengine.source.Account;
import com.utm.searchengine.source.ConfirmUser;
import com.utm.searchengine.source.Database;
import com.utm.searchengine.source.SecurityQuestion;
import com.utm.searchengine.source.ServeSessionID;

public class SecurityQuestionServlet extends HttpServlet{
	
	private Database db = new Database();
	private String user = "";
	private String question = "";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		  user = req.getParameter("user");
		  question = req.getParameter("question");
		  if (user == null || question == null) {
			  res.sendRedirect("/confirmUser");
			  return;
		  }
		  
		  req.setAttribute("question", question);
		  req.getRequestDispatcher("/WEB-INF/securityQuestion.jsp").forward(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req,
	                    HttpServletResponse resp)
	            throws ServletException, IOException
	{
		String answer = req.getParameter("answer");
		if (answer.equals("")) {
			req.setAttribute("error", "Please enter the answer of your security question.");
			req.setAttribute("question", question);
			req.getRequestDispatcher("/WEB-INF/securityQuestion.jsp").forward(req, resp);
			return;
		}
		try {
			if (SecurityQuestion.handleAnswer(user, answer, db)) {
				req.getSession().setAttribute("recover", true);
				resp.sendRedirect("/recoverPassword?user=" + user);
				return;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("error", "Incorrect answer. Please try again.");
		req.setAttribute("question", question);
		req.getRequestDispatcher("/WEB-INF/securityQuestion.jsp").forward(req, resp);
	}

}
