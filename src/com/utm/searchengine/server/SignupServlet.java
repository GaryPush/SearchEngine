package com.utm.searchengine.server;
import java.io.IOException;
import com.utm.searchengine.source.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.utils.SystemProperty;
import com.utm.searchengine.source.SignupUser;
@SuppressWarnings("serial")
public class SignupServlet extends HttpServlet {
	Database db = new Database();
	@Override
	 // Method to handle GET method request.
	  public void doPost(HttpServletRequest request,
	                    HttpServletResponse response)
	            throws ServletException, IOException
	  {
	      String[] output = new String[9];
	      output[0] = request.getParameter("username");
	      output[1] = request.getParameter("fName");
	      output[2] = request.getParameter("lName");
	      output[3] = request.getParameter("email");
	      output[4] = request.getParameter("p1");
	      output[5] = request.getParameter("p2");
	      output[6] = request.getParameter("User Type");
	      output[7] = request.getParameter("question");
	      output[8] = request.getParameter("answer");
	      String errMsg = "";
		try {
			errMsg = SignupUser.handleSignUp(output, db);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(errMsg.equals("")){
			//Signup worked
			response.sendRedirect("/login");
		}else{
			//Failed
			request.setAttribute("error", errMsg);
			request.getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
		}
	  }
		
	  
	  // Method to handle POST method request.
	  public void doGet(HttpServletRequest req,
	                     HttpServletResponse resp)
	      throws ServletException, IOException {
		  req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
	  }
}