package com.utm.searchengine.test;
import com.utm.searchengine.source.Database;
import junit.framework.*;
import static org.mockito.Mockito.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;

public class TestDatabase extends TestCase {
	
	Database db = mock(Database.class);
	
	@Rule
    public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testConnection() throws ClassNotFoundException, SQLException {
	    db.getConnection();
	}
	
	@Test
	public void testNoQuery() throws SQLException, ClassNotFoundException  {
		// Exception will automatically fail
		String query = "";
        String[] args = new String[] {};
        Connection conn = mock(Connection.class);
        boolean insert = false;
        exception.expect(NullPointerException.class);
	    assertTrue(db.handleQuery(query, args, conn, insert) == null);
	}
}