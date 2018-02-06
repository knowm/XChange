package org.knowm.xchange.abucoins;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AbucoinsAdaptersSplitIDsTest {

  @Test
  public void testSingleID() {
    String[] string = AbucoinsAdapters.adaptToSetOfIDs("[\"1111\"]");
    assertNotNull("null response", string);
    assertEquals("wrong number of strings", 1, string.length);
    assertEquals("Wrong value", "1111", string[0]);
  }

  @Test
  public void testMultipleIDs() {
    String[] string = AbucoinsAdapters.adaptToSetOfIDs("[\"1111\",\"2222\", \"3333\"]");
    assertNotNull("null response", string);
    assertEquals("wrong number of strings", 3, string.length);
    assertEquals("Wrong value", "1111", string[0]);
    assertEquals("Wrong value", "2222", string[1]);
    assertEquals("Wrong value", "3333", string[2]);
  }
}
