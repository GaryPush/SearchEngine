package com.utm.searchengine.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.UploadOptions;
import com.google.appengine.api.search.Query;
import com.utm.searchengine.source.SearchFile;
import com.utm.searchengine.source.ServeSessionID;


public class SearchServlet extends HttpServlet  {

  /**
   * Internal callback executed by Blobstore service when an uploaded
   * successfully inished.
   */
  public static final String SEARCH_CALLBACK = "/search";

  /**
   * Google Cloud Storage (GCS) bucket name where the uploaded content will
   * reside in.
   */
  public static final String GCS_BUCKET_NAME = "301userfiles";
  private String currentUser = "";
  private String accountUrl = "";
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
	  currentUser = ServeSessionID.getSessionID(req, "username");
	  if (currentUser == null){
		  System.out.println("hello");
		  resp.sendRedirect("/login");
		  return;
	  }
	  accountUrl = "/account?user=" + currentUser;
	  dispatchSearchForm(req, resp, null, null);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
	  currentUser = ServeSessionID.getSessionID(req, "username");
	  if (currentUser == null){
		  resp.sendRedirect("/login");
		  return;
	  }
	  resp.setContentType("text/html");
	  
      String userQuery = req.getParameter("keyword");
      String id = req.getParameter("id");
      String filter = req.getParameter("filter");
      
      Query query;
	  try {
		query = SearchFile.generateQuery(userQuery, id, filter);
		String output = SearchFile.searchDocs(query, userQuery, currentUser);
		dispatchSearchForm(req, resp, output, null);
	  } catch (Exception e) {
		// TODO Auto-generated catch block
		  System.out.println(e);
		req.setAttribute("error", "Search query contains illegal characters.");
		dispatchSearchForm(req, resp, null, "Search query contains illegal characters.");
	  }
  }
  
  protected void dispatchSearchForm(
          HttpServletRequest req,
          HttpServletResponse resp, 
          String msg,
          String err
  ) throws ServletException, IOException {

      req.setAttribute("accountUrl", accountUrl);
      
      if (msg != null) {
          req.setAttribute("text", msg);
      }
      if (err != null) {
    	  req.setAttribute("error", err);
      }
      req.getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
      
  }
 
}
 