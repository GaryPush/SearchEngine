package com.utm.searchengine.test;



import java.sql.ResultSet;
import com.utm.searchengine.server.*;
import com.utm.searchengine.source.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class TestLoginUser {
	LoginUser objectUnderTest;
	Database dbMock;
	ResultSet rsMock;
	PreparedStatement stmtMock;
	Connection connMock;
	String[] args= new String[6];
	
	@Before
	public void setup(){
		objectUnderTest = new LoginUser();
		rsMock = mock(ResultSet.class);
		stmtMock = mock(PreparedStatement.class);
		connMock = mock(Connection.class);
		dbMock = mock(Database.class);
	}
	 
	@Test
	public void userExistAndPasswordCorrect() throws ClassNotFoundException, SQLException, IOException{
		String query = "Select * from SearchProject.account where user_name=? and pass=?";

		  args[0] = "abbas";
		  args[1] = "abbas123";

		  when(dbMock.getConnection()).thenReturn(connMock); 
		  when(connMock.prepareStatement(query)).thenReturn(stmtMock);
		  doNothing().when(stmtMock).setString(anyInt(), anyString());  	  
		  when(rsMock.next()).thenReturn(true);
		  when(dbMock.handleQuery(query, new String[]{args[0], args[1]}, connMock, false)).thenReturn(rsMock);
		  assertEquals( "", LoginUser.handleLogin(args, dbMock));
		  
		
	}	 

	
	@Test
	public void userExistAndPasswordIncorrect() throws ClassNotFoundException, SQLException, IOException{
		String query = "Select * from SearchProject.account where user_name=? and pass=?";
		
		
		  args[0] = "abbas";
		  args[1] = "incorrectPass";
		  
		  
		  when(dbMock.getConnection()).thenReturn(connMock);
		  
		  when(connMock.prepareStatement(query)).thenReturn(stmtMock);
		  doNothing().when(stmtMock).setString(anyInt(), anyString());  	  
		  when(rsMock.next()).thenReturn(false);
		  when(dbMock.handleQuery(query, new String[]{args[0], args[1]}, connMock, false)).thenReturn(rsMock);
		  assertEquals( "Incorrect username or password.", LoginUser.handleLogin(args, dbMock));
		  
		
	}
	
	@Test
	public void userDoesNotExist() throws ClassNotFoundException, SQLException, IOException{
		String query = "Select * from SearchProject.account where user_name=? and pass=?";

		  args[0] = "fakeUser";
		  args[1] = "irrelaventPass";
		 
		  when(dbMock.getConnection()).thenReturn(connMock);
		  when(connMock.prepareStatement(query)).thenReturn(stmtMock);
		  doNothing().when(stmtMock).setString(anyInt(), anyString());  	  
		  when(rsMock.next()).thenReturn(false);
		  when(dbMock.handleQuery(query, new String[]{args[0], args[1]}, connMock, false)).thenReturn(rsMock);
		  assertEquals( "Incorrect username or password.", LoginUser.handleLogin(args, dbMock));
		  
		
	}

}