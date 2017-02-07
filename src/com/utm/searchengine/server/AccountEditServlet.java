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
import com.utm.searchengine.source.Database;
import com.utm.searchengine.source.ServeSessionID;
import com.utm.searchengine.source.ServeUrl;
import com.utm.searchengine.source.UploadFile;

public class AccountEditServlet extends HttpServlet{

    private String accountUrl = "";
    public static final String GCS_BUCKET_NAME = "301userpictures";
	
	private final BlobstoreService blobstore = 
            BlobstoreServiceFactory.getBlobstoreService();
	private Database db = new Database();
	public String user = "";
    
	@Override
	  public void doPost(HttpServletRequest request,
	                    HttpServletResponse response)
	            throws ServletException, IOException
	  {
		
		String currentUser = ServeSessionID.getSessionID(request, "username");
      	if (currentUser == null){
  		  response.sendRedirect("/login");
  		  return;
  	    }
      	
	      String[] output = new String[5];
	      output[0] = request.getParameter("fName");
	      output[1] = request.getParameter("lName");
	      output[2] = request.getParameter("email");
	      output[3] = "";
	      output[4] = user;
	      
	      List<FileInfo> fileInfos = blobstore.getFileInfos(request).get("image");
	      output[3] = UploadFile.handleUploadPhoto(fileInfos);
	      if (output[3] == null) {
	    	  output[3] = "";
	      }
	      if (output[3].equals("error")) {
	    	  dispatchAccountEditForm(request, response, "Invalid type of picture.");
	    	  return;
	      }
	      try {
			String errMsg = AccountEdit.handleEdit(output, db);
			if (!errMsg.equals("")) {
				dispatchAccountEditForm(request, response, errMsg);
				return;
			}
			response.sendRedirect("/account?user=" + user);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	  }
		
	  
	  public void doGet(HttpServletRequest req,
	                     HttpServletResponse resp)
	      throws ServletException, IOException {
		  String currentUser = ServeSessionID.getSessionID(req, "username");
		  accountUrl = "/account?user=" + currentUser;
		  user = req.getParameter("user");
	      	if (currentUser == null){
	  		  resp.sendRedirect("/login");
	  		  return;
	  	    }
	      if (currentUser.equals("admin")){
	    	  dispatchAccountEditForm(req, resp, null);
	      }
		  if (user == null || !user.equals(currentUser)) {
			  resp.sendRedirect("/accountEdit?user=" + currentUser);
			  return;
		  }
		  dispatchAccountEditForm(req, resp, null);
	  }
	  
	  protected void dispatchAccountEditForm(
	            HttpServletRequest req,
	            HttpServletResponse resp, 
	            String msg
	    ) throws ServletException, IOException {
	        UploadOptions opts = UploadOptions.Builder
	                .withGoogleStorageBucketName(GCS_BUCKET_NAME);
	        String uploadUrl = blobstore.createUploadUrl("/accountEdit", opts);

	        req.setAttribute("uploadUrl", uploadUrl);
		    req.setAttribute("accountUrl", accountUrl);
	        if (msg != null) {
	            req.setAttribute("error", msg);
	        }
	        req.getRequestDispatcher("/WEB-INF/accountEdit.jsp").forward(req, resp);
	        
	    }
}
