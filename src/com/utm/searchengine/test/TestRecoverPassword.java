package com.utm.searchengine.test;
import com.utm.searchengine.source.RecoverPassword;
import com.utm.searchengine.source.Database;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.utm.searchengine.source.ConfirmUser;

public class TestRecoverPassword {
  
  RecoverPassword recoverpassword = mock(RecoverPassword.class);
  
  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  public void testHandleRecovery() throws ClassNotFoundException, SQLException {
    String username = "SwervinPervan";
    String password = "password";
    Database db = mock(Database.class);
    recoverpassword.handleRecovery(username, password, db);
    verify(db).getConnection();
  }

}
