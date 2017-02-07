package com.utm.searchengine.test;



import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.FileInfo;
import com.utm.searchengine.server.*;
import com.utm.searchengine.source.Database;
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

import org.junit.Test;
import org.mockito.Mock;
import java.util.List;
public class TestUploadFile {
	
	@Test
	public void NoFileUpload() throws IOException{
		List<FileInfo> mockList = mock(List.class);
		List<BlobKey> mockBlob = mock(List.class);
		when(mockList.size()).thenReturn(0);
		assertEquals("Upload Success!",UploadFile.handleUploadFile(mockList, mockBlob, "abbas", "csc301", "instructor") );
	}
	
	@Test
	public void IdentityDoesNotExistsInDatabase() throws ClassNotFoundException, SQLException, IOException{
		Connection connMock = mock(Connection.class);
		Database dbMock = mock(Database.class);
		ResultSet rsMock = mock(ResultSet.class);
		String query="Select identity from SearchProject.account where user_name=?";
		when(dbMock.getConnection()).thenReturn(connMock);
		when(dbMock.handleQuery(query, new String[]{"abbas"}, connMock, false)).thenReturn(rsMock);
		when(rsMock.next()).thenReturn(false);
		assertEquals("", UploadFile.getIdentityFromDatabase("abbas", dbMock));
	}
	
	@Test
	public void IdentitytExistsInDatabase() throws ClassNotFoundException, SQLException, IOException{
		Connection connMock = mock(Connection.class);
		Database dbMock = mock(Database.class);
		ResultSet rsMock = mock(ResultSet.class);
		String query="Select identity from SearchProject.account where user_name=?";
		when(dbMock.getConnection()).thenReturn(connMock);
		when(dbMock.handleQuery(query, new String[]{"abbas"}, connMock, false)).thenReturn(rsMock);
		when(rsMock.next()).thenReturn(true);
		when(rsMock.getString("identity")).thenReturn("instructor");
		assertEquals("instructor", UploadFile.getIdentityFromDatabase("abbas", dbMock));
	}

}