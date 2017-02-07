package com.utm.searchengine.source;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchException;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;

public class SearchFile {
	
	private static final String pattern = "[<>%$:]";

	public static Query generateQuery(String userQuery, String id, String filter) throws Exception{

	    try {
	        // Apply filters
	        String queryString = userQuery;
	        if(!filter.equals("all") && !userQuery.equals("")){
	          queryString = filter + ":" + userQuery; 
	        }
	        if (!id.equals("all")){
	          queryString += " identity:" + id;
	        }

	        // Build the QueryOptions
	        QueryOptions options = QueryOptions.newBuilder()
	            .setLimit(25)
	            //.setFieldsToReturn("name", "id", "content", "published", "url")
	            //.setSortOptions(sortOptions)
	            .build();
	            
	        //  Build the Query
	        System.out.println(queryString);
	        if (isValid(queryString)){
	            Query query = Query.newBuilder().setOptions(options).build(queryString);
	            return query;
	        }else{
	        	throw new Exception();
	        }
	        
	        
	    } catch (SearchException e) {
	        // handle exception...
	      return null;
	    }
	  }
	  
	  public static String searchDocs(Query query, String userQuery, String currentUser) {
	    try {
	      IndexSpec indexSpec = IndexSpec.newBuilder().setName("Userfiles").build();
	      Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
	      Results<ScoredDocument> results =  index.search(query);
	      Boolean docDesired;
	      String output = "<hr>";
	      if (results.getNumberFound() == 0){
	    	  output = "Your search did not match any documents.";
	      }
	      // Iterate over the documents in the results
	      int count = 1;
	      for (ScoredDocument document : results) {
	        // handle results
	        docDesired = true;
	    	String filename = "";
	      	String url = "";
	      	String date = "";
	      	String type = "";
	      	String content = "";
	      	String uploader = "";
	      	String course = "";
	      	String identity = "";
	      	String docID = document.getId();
	      	
	        for (Field f : document.getFields()){
	        	if (f.getName().equals("name")) {
	        		filename = f.getText();
	        	} else if (f.getName().equals("type")) {
	        		type = f.getText();
	        	} else if (f.getName().equals("content")) {
	        		if (type.equals("TXT") || type.equals("PDF")){
	            		content = f.getText();
	        		} else if (type.equals("HTML")){
	        			content = f.getHTML();
	        		}
	        	} else if (f.getName().equals("url")) {
	        		url = f.getAtom();
	        	} else if (f.getName().equals("published")) {
	        		date = f.getDate().toString();
	        	} else if (f.getName().equals("course")) {
	        		course = f.getAtom();
	        	}else if (f.getName().equals("uploader")) {
	        		uploader = f.getText();
	        	}else if (f.getName().equals("identity")) {
	        		identity = f.getText();
	        	}
	        }
	        String partialContent = getPartialContent(content, userQuery, type);
	        if (partialContent.equals("")){
	        	output += count + ". [" + type + "]" + "<a href=\"" + url +"\">" + filename + "</a><br>"
	    		        + "<p>Uploader: " 
	        			+ "<a href=\"/account?user=" + uploader  + "\">" + uploader + "</a>"
	        			+ " (" + identity + ")"
	    		        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
	    		        + "Course: " + course
	    		        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	    		        + "Published Date: " + date;
	        } else {
	    		output += count + ". [" + type + "]" + "<a href=\"" + url +"\">" + filename + "</a><br>"
	    				+ partialContent + "..." + "<br>"
	    		        + "<p>Uploader: " 
	    		        + "<a href=\"/account?user=" + uploader  + "\">" + uploader + "</a>"
	    				+ " (" + identity + ")"
	    		        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
	    		        + "Course: " + course
	    		        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	    		        + "Published Date: " + date;
	        }
	        if (currentUser.equals(uploader) || currentUser.equals("admin")){
	        	output += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	        			+ "<a href=\"/delete?uploader=" + uploader 
	        			+ "&docID=" + docID 
	        			+ "\">delete</a>";
	        }
	        
	        output += "<br><hr>";
			count += 1;
	      }
	      return output;
	    } catch (SearchException e) {
	        if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
	            // retry
	          return "";
	        }
	    }
	    return "";
	  }
	  
	  
	  private static Boolean isValid(String queryString) {
	  	Pattern p = Pattern.compile(pattern);
	  	Matcher m = p.matcher(queryString);
	  	if (m.matches())
	  	{
	  	    // Invalid input: reject it, or remove/change the offending characters.
	  		return false;
	  	}
	  	else
	  	{
	  	    // Valid input.
	  		return true;
	  	}
	  }
	  
	  private static String getPartialContent(String content, String userQuery, String type){
		  
		  String tmpContent = content.toLowerCase(Locale.US);
		  userQuery = userQuery.toLowerCase(Locale.US);
		  int size = 125;
		  int limit = 300;

		  if (type.equals("HTML")){
			  content = Jsoup.parse(content).text();
			  tmpContent = content.toLowerCase(Locale.US);
		  }
		  	if (tmpContent.length() != 0){
				
				int index = tmpContent.indexOf(userQuery);
				int start = index - size;
				int end = index + size;
				
				if (index == -1){
					if (content.length() > 0) {
						if (content.length() <= limit) {
							return content;
						}
						return content.substring(0, limit);
				  	}
					return "";
				}
				if (end >= tmpContent.length()) {
					end = tmpContent.length() - 1;
				} else {
					while (tmpContent.charAt(end) != ' '){
						if (end - start > limit) {
							break;
						}
						end += 1;
						if (end >= tmpContent.length()) {
							end = tmpContent.length() - 1;
							break;
						}
					}
				}
				if (start < 0) {
					start = 0;
				} else {
					while (tmpContent.charAt(start) != ' '){
						if (end - start > limit) {
							break;
						}
						start -= 1;
						if (start < 0) {
							start = 0;
							break;
						}
					}
				}
				return content.substring(start, end);
		  	}
		  	if (content.length() > 0) {
				if (content.length() <= limit) {
					return content;
				}
				return content.substring(0, limit);
		  	}
			return "";
	  }
}
