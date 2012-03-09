package com.xeiam.xchange;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SymbolPairTest {

  @Test
  public void testMajors() {

    assertEquals("EUR", SymbolPair.EUR_USD.baseSymbol);
    assertEquals("USD", SymbolPair.EUR_USD.counterSymbol);

    assertEquals("GBP", SymbolPair.GBP_USD.baseSymbol);
    assertEquals("USD", SymbolPair.GBP_USD.counterSymbol);

    assertEquals("USD", SymbolPair.USD_JPY.baseSymbol);
    assertEquals("JPY", SymbolPair.USD_JPY.counterSymbol);

    assertEquals("USD", SymbolPair.USD_CHF.baseSymbol);
    assertEquals("CHF", SymbolPair.USD_CHF.counterSymbol);

    assertEquals("USD", SymbolPair.USD_AUD.baseSymbol);
    assertEquals("AUD", SymbolPair.USD_AUD.counterSymbol);

    assertEquals("USD", SymbolPair.USD_CAD.baseSymbol);
    assertEquals("CAD", SymbolPair.USD_CAD.counterSymbol);
  }

  @Test
  public void testBitcoinCourtesy() {

    assertEquals("BTC", SymbolPair.BTC_USD.baseSymbol);
    assertEquals("USD", SymbolPair.BTC_USD.counterSymbol);

    assertEquals("BTC", SymbolPair.BTC_GBP.baseSymbol);
    assertEquals("USD", SymbolPair.BTC_USD.counterSymbol);

    assertEquals("BTC", SymbolPair.BTC_EUR.baseSymbol);
    assertEquals("EUR", SymbolPair.BTC_EUR.counterSymbol);

    assertEquals("BTC", SymbolPair.BTC_JPY.baseSymbol);
    assertEquals("JPY", SymbolPair.BTC_JPY.counterSymbol);

    assertEquals("BTC", SymbolPair.BTC_CHF.baseSymbol);
    assertEquals("CHF", SymbolPair.BTC_CHF.counterSymbol);

    assertEquals("BTC", SymbolPair.BTC_AUD.baseSymbol);
    assertEquals("AUD", SymbolPair.BTC_AUD.counterSymbol);

    assertEquals("BTC", SymbolPair.BTC_CAD.baseSymbol);
    assertEquals("CAD", SymbolPair.BTC_CAD.counterSymbol);

  }

  @Test
  public void testValidation() {
    
    String[][] testCases = new String[][] {
      {null,"USD"},
      {"BTC",null},
      {null,null},
      {"BTCA","USD"},
      {"BT","USD"},
      {"BTC","USDA"},
      {"BTC","US"},
    };

    for (int i=0; i<testCases.length; i++) {
      try {
        new SymbolPair(testCases[i][0],testCases[i][1]);
        fail("Expected exception for "+testCases[i][0]+" "+testCases[i][1]);
      } catch (IllegalArgumentException e) {
        // Do nothing
      }

    }
  }
}
