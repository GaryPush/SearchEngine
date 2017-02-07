package com.utm.searchengine.test;


import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.FileInfo;
import com.utm.searchengine.server.*;
import com.utm.searchengine.source.Database;
import com.utm.searchengine.source.SecurityQuestion;
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

public class TestSignup {
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
	public void CreateUserAlreadyCreated() throws ClassNotFoundException, SQLException{
		String[] args= new String[9];
		  args[0] = "abbas";
		  args[1] = "Test";
		  args[2] = "User";
		  args[3] = "test@user.com";
		  args[4] = "abbas123";
		  args[5] = "abbas123";
		  args[6] = "student";
		  args[7] = "pic.png";
		  args[8] = "canada";
		  String query="Select * from SearchProject.account where user_name=?";
		  when(dbMock.getConnection()).thenReturn(connMock);
		  when(dbMock.handleQuery(query, new String[]{args[0]}, connMock, false)).thenReturn(rsMock);
		  when(rsMock.isBeforeFirst()).thenReturn(true);
		  assertEquals("User name already exists.", SignupUser.handleSignUp(args, dbMock));
	}
	
	@Test
	public void CreateUserPasswordsIncorrect() throws ClassNotFoundException, SQLException{
		String[] args= new String[9];
		  args[0] = "abbas";
		  args[1] = "Test";
		  args[2] = "User";
		  args[3] = "test@user.com";
		  args[4] = "abbas1234";
		  args[5] = "abbas123";
		  args[6] = "student";
		  args[7] = "pic.png";
		  args[8] = "canada";
		  String query="Select * from SearchProject.account where user_name=?";
		  when(dbMock.getConnection()).thenReturn(connMock);
		  when(dbMock.handleQuery(query, new String[]{args[0]}, connMock, false)).thenReturn(rsMock);
		  when(rsMock.isBeforeFirst()).thenReturn(true);
		  assertEquals("Passwords not matching.", SignupUser.handleSignUp(args, dbMock));
	}
	@Test
	public void CreateUserPasswordsLengthShort() throws ClassNotFoundException, SQLException{
		String[] args= new String[9];
		  args[0] = "abbas";
		  args[1] = "Test";
		  args[2] = "User";
		  args[3] = "test@user.com";
		  args[4] = "ab";
		  args[5] = "ab";
		  args[6] = "student";
		  args[7] = "pic.png";
		  args[8] = "canada";
		  String query="Select * from SearchProject.account where user_name=?";
		  when(dbMock.getConnection()).thenReturn(connMock);
		  when(dbMock.handleQuery(query, new String[]{args[0]}, connMock, false)).thenReturn(rsMock);
		  when(rsMock.isBeforeFirst()).thenReturn(true);
		  assertEquals("Password must be at least 6 characters long.", SignupUser.handleSignUp(args, dbMock));
	}
}