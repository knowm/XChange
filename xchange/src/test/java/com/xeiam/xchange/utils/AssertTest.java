package com.xeiam.xchange.utils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AssertTest {

  @Test
  public void testNotNull() {
    
    Assert.notNull("","Not null");
    
    try {
      Assert.notNull(null,"null");
      fail("Expected exception");
    } catch (IllegalArgumentException e) {
      assertEquals("null",e.getMessage());
    }
    
  }

  @Test
  public void testHasLength() {

    Assert.hasLength("Test", 4, "Wrong length");

    try {
      Assert.hasLength(null, 4, "null");
      fail("Expected exception");
    } catch (IllegalArgumentException e) {
      assertEquals("null",e.getMessage());
    }

    try {
      Assert.hasLength("",4, "short");
      fail("Expected exception");
    } catch (IllegalArgumentException e) {
      assertEquals("short",e.getMessage());
    }

  }

  @Test
  public void testHasSize() {

    Assert.hasSize(Arrays.asList("1", "2", "3"), 3, "Wrong length");

    try {
      Assert.hasSize(null, 4, "null");
      fail("Expected exception");
    } catch (IllegalArgumentException e) {
      assertEquals("null",e.getMessage());
    }

    try {
      Assert.hasSize(Arrays.asList("1","2","3"), 4, "short");
      fail("Expected exception");
    } catch (IllegalArgumentException e) {
      assertEquals("short",e.getMessage());
    }

  }


}
