package com.utm.searchengine.test;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.FileInfo;
import com.utm.searchengine.server.*;
import com.utm.searchengine.source.Database;
import com.utm.searchengine.source.SecurityQuestion;
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

public class TestSecurityQuestion {
	@Test
	public void SecurityQuestionsWithCorrectAnswer() throws SQLException, ClassNotFoundException{
		Database dbMock = mock(Database.class);
		Connection connMock = mock(Connection.class);
		ResultSet mockRs = mock(ResultSet.class);
		when(dbMock.getConnection()).thenReturn(connMock);
		String query = "Select * from SearchProject.account where user_name=? and answer=?";
		when(dbMock.handleQuery(query, new String[]{"abbas", "canada"}, connMock, false)).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(true);
		assertEquals(true,SecurityQuestion.handleAnswer("abbas", "canada", dbMock));
		
	}
	@Test
	public void SecurityQuestionsWithIncorrectAnswer() throws SQLException, ClassNotFoundException{
		Database dbMock = mock(Database.class);
		Connection connMock = mock(Connection.class);
		ResultSet mockRs = mock(ResultSet.class);
		when(dbMock.getConnection()).thenReturn(connMock);
		String query = "Select * from SearchProject.account where user_name=? and answer=?";
		when(dbMock.handleQuery(query, new String[]{"abbas", "canada"}, connMock, false)).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(false);
		assertEquals(false,SecurityQuestion.handleAnswer("abbas", "canada", dbMock));
		
	}
	
	
}