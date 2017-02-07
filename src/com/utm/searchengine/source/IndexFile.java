package com.utm.searchengine.source;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GetRequest;
import com.google.appengine.api.search.GetResponse;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;

public class IndexFile {
	
	public static final String GCS_BUCKET_NAME = "301userfiles";
	public static final String GCS_INDEX_NAME = "Userfiles";
	
	private final String fileName;
	private final String type;
	private final String content;
	private final String blobkey;
	private final String url;
	private final String course;
	private final String identity;
	private final String username;
	
	public static class Builder {
		private String fileName;
		private String type;
		private String content;
		private String blobkey;
		private String url;
		private String course;
		private String identity;
		private String username;
		
		public Builder fileName(String fileName){this.fileName = fileName; return this; }
	    public Builder type(String type){this.type = type; return this; }
	    public Builder content(String content){this.content = content; return this; }
	    public Builder blobkey(String blobkey){this.blobkey = blobkey; return this; }
	    public Builder url(String url){this.url = url; return this; }
	    public Builder course(String course){this.course = course; return this; }
	    public Builder identity(String identity){this.identity = identity; return this; }
	    public Builder username(String username){this.username = username; return this; }
	    
	    public IndexFile build() {
	    	return new IndexFile(this);
	    }
	}	
	
	private IndexFile(Builder builder) {
		this.fileName = builder.fileName;
		this.type = builder.type;
		this.content = builder.content;
		this.blobkey = builder.blobkey;
		this.url = builder.url;
		this.course = builder.course;
		this.identity = builder.identity;
		this.username = builder.username;
		
	}

	public void createDocument(){
    	Document doc = null;
    	String myDocId = blobkey;
    	if (type.equals("HTML")){
    		doc = Document.newBuilder()
    	    	    .setId(myDocId) // Setting the document identifer is optional. If omitted, the search service will create an identifier.
    	    	    .addField(Field.newBuilder().setName("name").setText(fileName))
    	    	    .addField(Field.newBuilder().setName("type").setText(type))
    	    	    .addField(Field.newBuilder().setName("content").setHTML(content))
    	    	    .addField(Field.newBuilder().setName("blobkey").setAtom(blobkey))
    	    	    .addField(Field.newBuilder().setName("url").setAtom(url))
    	    	    .addField(Field.newBuilder().setName("course").setAtom(course))
    	    	    .addField(Field.newBuilder().setName("identity").setText(identity))
    	    	    .addField(Field.newBuilder().setName("uploader").setText(username))
    	    	    .addField(Field.newBuilder().setName("published").setDate(new Date()))
    	    	    .build();
    	} else if (type.equals("TXT") || type.equals("PDF")){
    		doc = Document.newBuilder()
    	    	    .setId(myDocId) // Setting the document identifer is optional. If omitted, the search service will create an identifier.
    	    	    .addField(Field.newBuilder().setName("name").setText(fileName))
    	    	    .addField(Field.newBuilder().setName("type").setText(type))
    	    	    .addField(Field.newBuilder().setName("content").setText(content))
    	    	    .addField(Field.newBuilder().setName("blobkey").setAtom(blobkey))
    	    	    .addField(Field.newBuilder().setName("url").setAtom(url))
    	    	    .addField(Field.newBuilder().setName("course").setAtom(course))
    	    	    .addField(Field.newBuilder().setName("identity").setText(identity))
    	    	    .addField(Field.newBuilder().setName("uploader").setText(username))
    	    	    .addField(Field.newBuilder().setName("published").setDate(new Date()))
    	    	    .build();
    	}
    	if (username.equals("admin")){ // for testing purpose
        	removeAllDocuments();
    	} else {
    		indexADocument(doc);
    	}
    }
	
	private void indexADocument(Document document) {
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(GCS_INDEX_NAME).build(); 
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
        
        try {
            index.put(document);
        } catch (PutException e) {
            if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
                // retry putting the document
            }
        }
    }
    
    private void removeAllDocuments() {
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(GCS_INDEX_NAME).build(); 
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
        
        try {
            // looping because getRange by default returns up to 100 documents at a time
            while (true) {
                List<String> docIds = new ArrayList<String>();
                // Return a set of doc_ids.
                GetRequest request = GetRequest.newBuilder().setReturningIdsOnly(true).build();
                GetResponse<Document> response = index.getRange(request);
                if (response.getResults().isEmpty()) {
                    break;
                }
                for (Document doc : response) {
                    docIds.add(doc.getId());
                }
                index.delete(docIds);
                System.out.println("delete done");
            }
        } catch (RuntimeException e) {
            
        }
    }
    
    public static void removeADocument(String docID) {
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(GCS_INDEX_NAME).build(); 
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
        
        index.delete(docID);
    }
}
