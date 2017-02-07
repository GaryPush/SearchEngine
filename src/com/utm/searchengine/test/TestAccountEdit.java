package com.utm.searchengine.test;
import com.utm.searchengine.source.AccountEdit;
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

public class TestAccountEdit {
  
  AccountEdit accountedit = new AccountEdit();
  
  @Test
  public void testHandleEdit() throws ClassNotFoundException, SQLException {
    String[] args = new String[] {"Abbas","","","","",""};
    Database db = mock(Database.class);
    assertTrue(accountedit.handleEdit(args, db) == "");
  }
}
