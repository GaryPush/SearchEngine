package com.utm.searchengine.test;


import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.FileInfo;
import com.utm.searchengine.server.*;
import com.utm.searchengine.source.Database;
import com.utm.searchengine.source.SecurityQuestion;
import com.utm.searchengine.source.ServeUrl;
import com.utm.searchengine.source.SignupUser;
import com.utm.searchengine.source.UploadFile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import java.util.List;

public class TestServeURL {
	
	Database dbMock;
	Connection connMock;
	ResultSet rsMock;
	@Before
	public void setup(){
		dbMock = mock(Database.class);
		connMock = mock(Connection.class);
		rsMock = mock(ResultSet.class);
	}
	
	@Test
	public void testGuessServingURL(){
		FileInfo file = mock(FileInfo.class);
		ServeUrl serve = new ServeUrl();
		serve.setDevMode(true);
		assertEquals("/dev-does-not-support-gcs-serving-yet",serve.guessServingUrl(file));
	}
	
	
}
