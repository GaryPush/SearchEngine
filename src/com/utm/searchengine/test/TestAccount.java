package com.utm.searchengine.test;
import com.utm.searchengine.source.Account;
import com.utm.searchengine.source.Database;

import static org.junit.Assert.*;

import junit.framework.*;

import static org.mockito.Mockito.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;

import org.junit.Test;

public class TestAccount {
  
  Account account = new Account();
  
  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  public void testFindAsGuest() throws ClassNotFoundException, SQLException {
    String owner = "abbas";
    Database db = mock(Database.class);
    exception.expect(NullPointerException.class);
    assertTrue(account.createAccountInfoAsGuest(owner, db) == "");
  }
  
  @Test
  public void testFindAsOwner() throws ClassNotFoundException, SQLException {
    String owner = "abbas";
    Database db = mock(Database.class);
    exception.expect(NullPointerException.class);
    assertTrue(account.createAccountInfoAsOwner(owner, db) == "");
  }
}
