package com.utm.searchengine.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.utm.searchengine.source.IndexFile;
import com.utm.searchengine.source.ServeSessionID;

public class DeleteServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String currentUser = ServeSessionID.getSessionID(req, "username");
      	if (currentUser == null){
  		  res.sendRedirect("/login");
  		  return;
  	    }
		  
		String uploader = req.getParameter("uploader");
		String docID = req.getParameter("docID");
		
		if (uploader == null || docID == null || 
				uploader.equals(currentUser) || currentUser.equals("admin")) {
			IndexFile.removeADocument(docID);
		}
		res.sendRedirect("/search");
	}
	
	
}
