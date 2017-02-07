package com.utm.searchengine.test;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.FileInfo;
import com.utm.searchengine.server.*;
import com.utm.searchengine.source.ConfirmUser;
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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import java.util.List;

public class TestConfirmUser {
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
	public void confirmUserExist() throws ClassNotFoundException, SQLException{
		Database dbMock = mock(Database.class);
		Connection connMock = mock(Connection.class);
		ResultSet rsMock = mock(ResultSet.class);
		when (dbMock.getConnection()).thenReturn(connMock);
		String query=  "Select * from SearchProject.account where user_name=?";
		when(dbMock.handleQuery(query, new String[]{"abbas"}, connMock, false)).thenReturn(rsMock);
		when(rsMock.next()).thenReturn(true);
		when(rsMock.getString("question")).thenReturn("answer");
		assertEquals("answer",ConfirmUser.handleConfirm("abbas", dbMock));
		
	}
	
	@Test
	public void confirmUserDoesNotExist() throws ClassNotFoundException, SQLException{
		
		when (dbMock.getConnection()).thenReturn(connMock);
		String query=  "Select * from SearchProject.account where user_name=?";
		when(dbMock.handleQuery(query, new String[]{"abbas"}, connMock, false)).thenReturn(rsMock);
		when(rsMock.next()).thenReturn(false);
		//when(rsMock.getString("question")).thenReturn("answer");
		assertEquals("error",ConfirmUser.handleConfirm("abbas", dbMock));
		
	}
	
	
}