package com.utm.searchengine.server;
import com.utm.searchengine.source.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.FileInfo;
import com.google.appengine.api.blobstore.UploadOptions;
import com.utm.searchengine.source.ServeSessionID;
import com.utm.searchengine.source.UploadFile;

public class UploadServlet extends HttpServlet {
	public static final String GCS_BUCKET_NAME = "301userfiles";
	public static final String GCS_INDEX_NAME = "Userfiles";
	private String currentUser = "";
    private String identity = "";
    private String course = "";
    private String accountUrl = "";
	Database db = new Database();
    private final BlobstoreService blobstore = 
            BlobstoreServiceFactory.getBlobstoreService();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	currentUser = ServeSessionID.getSessionID(req, "username");
      	if (currentUser == null){
  		  resp.sendRedirect("/login");
  		  return;
  	    }
      	accountUrl = "/account?user=" + currentUser;
      	try {
			identity = UploadFile.getIdentityFromDatabase(currentUser, db);
			if (identity.equals("i")){
				identity = "Instructor";
			} else if (identity.equals("s")){
				identity = "Student";
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        dispatchUploadForm(req, resp, null);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
    	currentUser = ServeSessionID.getSessionID(req, "username");
      	if (currentUser == null){
  		  resp.sendRedirect("/login");
  		  return;
  	    }
      	
        List<FileInfo> fileInfos = blobstore.getFileInfos(req).get("file");
        List<BlobKey> blobkeys = blobstore.getUploads(req).get("file");
        course = req.getParameter("course");
        if (course.equals("")){
        	course = "N/A";
        }
        
        String output = UploadFile.handleUploadFile(fileInfos, blobkeys, currentUser, course, identity);
        
        dispatchUploadForm(req, resp, output);
    }
    
    protected void dispatchUploadForm(
            HttpServletRequest req,
            HttpServletResponse resp, 
            String msg
    ) throws ServletException, IOException {
        UploadOptions opts = UploadOptions.Builder
                .withGoogleStorageBucketName(GCS_BUCKET_NAME);
        String uploadUrl = blobstore.createUploadUrl("/upload", opts);

        req.setAttribute("uploadUrl", uploadUrl);
        req.setAttribute("accountUrl", accountUrl);
        if (msg != null) {
            req.setAttribute("text", msg);
        }
        req.getRequestDispatcher("/WEB-INF/upload.jsp").forward(req, resp);
        
    }
   
}
