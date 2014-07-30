package com.xeiam.xchange.btcchina.service;

import static org.junit.Assert.assertEquals;
import junit.extensions.PA;

import org.junit.Test;

public class BTCChinaDigestTest {

  private BTCChinaDigest digest = BTCChinaDigest.createInstance("hello", "world");

  @Test
  public void testStripParams() {

    String params = "\"BTC\",true";
    String stripped = (String) PA.invokeMethod(digest, "stripParams(java.lang.String)", params);
    assertEquals("BTC,1", stripped);

    params = "\"BTC\",false";
    stripped = (String) PA.invokeMethod(digest, "stripParams(java.lang.String)", params);
    assertEquals("BTC,", stripped);

    params = "\"BTC\",false,1";
    stripped = (String) PA.invokeMethod(digest, "stripParams(java.lang.String)", params);
    assertEquals("BTC,,1", stripped);
  }

}
