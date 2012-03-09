package com.xeiam.xchange.utils;

import org.junit.Test;

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
}
