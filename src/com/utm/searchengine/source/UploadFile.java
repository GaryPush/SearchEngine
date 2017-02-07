package com.utm.searchengine.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.FileInfo;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class UploadFile {
	
	private final static ServeUrl serveUrl = new ServeUrl();
	private final static long sizeLimit = 5000000; // 5MB

	public static String handleUploadFile(List<FileInfo> fileInfos, List<BlobKey> blobkeys, 
			String username, String course, String identity) throws IOException {
		
		for (int i=0; i < fileInfos.size(); i++) {
            String fileName = fileInfos.get(i).getFilename();
            String content = "";
            
            if (!fileInfos.get(i).getFilename().equals("")) {
            	String type = fileInfos.get(i).getContentType();
            	String blobkey = blobkeys.get(i).getKeyString();
            	String url = serveUrl.guessServingUrl(fileInfos.get(i));
            	long size = fileInfos.get(i).getSize();
            	System.out.println("Size: "+ size);
            	if (size > sizeLimit) {
            		return "File too large. Should be at most 5MB.";
            	}
                
                if (type.equals("text/html")){
                	content = readText(blobkeys.get(i));
                	type = "HTML";
                } else if (type.equals("text/plain")) {
                	content = readText(blobkeys.get(i));
                	type = "TXT";
                } else if (type.equals("application/pdf")){
                	content = readPDF(blobkeys.get(i));
                	type = "PDF";
                } else {
                    return "Must be .txt or .html or .pdf file.";
                } 
                IndexFile indexFile = new IndexFile.Builder()
                		.fileName(fileName)
                		.type(type)
                		.content(content)
                		.blobkey(blobkey)
                		.url(url)
                		.course(course)
                		.identity(identity)
                		.username(username)
                		.build();
                indexFile.createDocument();
            } else {
            	return "No file selected.";
            }
            
        }
		
		return "Upload Success!";
	}
	
	public static String handleUploadPhoto(List<FileInfo> fileInfos) {
		for (int i=0; i < fileInfos.size(); i++) {
	    	  String type = fileInfos.get(i).getContentType();
	    	  if (!fileInfos.get(i).getFilename().equals("")) {
	    		  System.out.println("name: " + fileInfos.get(i).getFilename());
	    		  if (type.contains("image")){
		        	  return serveUrl.guessServingUrl(fileInfos.get(i));
		          } else {
			          return "error";
		          }
	    	  }
	    }
  	  	return "";
	}
	
    
    public static String getIdentityFromDatabase(String username, Database db) 
    		throws SQLException, ClassNotFoundException, IOException {
    	
    	Connection conn = db.getConnection();
    	String query="Select identity from SearchProject.account where user_name=?";
    	ResultSet rs = db.handleQuery(query, new String[]{username}, conn, false);
    	if (rs.next()){
    		return rs.getString("identity");
    	}
    	return "";
    }
	 
    private static String readText(BlobKey blobkey){
    	String content = "";
		String line;
    	BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(new BlobstoreInputStream(blobkey)));
			while((line = reader.readLine()) != null) {
	        	content += (line + "\n");
	        }
	        reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return content;
    }
    
    private static String readPDF(BlobKey blobkey) throws IOException{
    	String text = "";
    	
    	InputStream is = new BlobstoreInputStream(blobkey);
    	PdfReader reader = new PdfReader(is);
    	PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        TextExtractionStrategy strategy;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
            text += strategy.getResultantText();
        }
    	
		return text;
    }
    
}
