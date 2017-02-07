package com.utm.searchengine.test;
import com.utm.searchengine.source.IndexFile;
import com.utm.searchengine.source.IndexFile.*;

import org.junit.rules.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TestIndexFile {
  
  Builder builder = mock(Builder.class);
  IndexFile indexfile = mock(IndexFile.class);
  
  @Rule
  public final ExpectedException exception = ExpectedException.none();
  
  @Test
  public void testBuilder() {
    builder.build();
  }
  
  @Test
  public void testCreateDocument() {
    indexfile.createDocument();
  }
  
  @Test
  public void testRemoveADocument() {
    String docID = "newdoc";
    exception.expect(NullPointerException.class);
    indexfile.removeADocument(docID);
  }

}

