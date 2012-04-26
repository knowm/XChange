package com.xeiam.xchange;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SymbolPairTest {

  @Test
  public void testMajors() {

    assertEquals("EUR", CurrencyPair.EUR_USD.baseSymbol);
    assertEquals("USD", CurrencyPair.EUR_USD.counterSymbol);

    assertEquals("GBP", CurrencyPair.GBP_USD.baseSymbol);
    assertEquals("USD", CurrencyPair.GBP_USD.counterSymbol);

    assertEquals("USD", CurrencyPair.USD_JPY.baseSymbol);
    assertEquals("JPY", CurrencyPair.USD_JPY.counterSymbol);

    assertEquals("USD", CurrencyPair.USD_CHF.baseSymbol);
    assertEquals("CHF", CurrencyPair.USD_CHF.counterSymbol);

    assertEquals("USD", CurrencyPair.USD_AUD.baseSymbol);
    assertEquals("AUD", CurrencyPair.USD_AUD.counterSymbol);

    assertEquals("USD", CurrencyPair.USD_CAD.baseSymbol);
    assertEquals("CAD", CurrencyPair.USD_CAD.counterSymbol);
  }

  @Test
  public void testBitcoinCourtesy() {

    assertEquals("BTC", CurrencyPair.BTC_USD.baseSymbol);
    assertEquals("USD", CurrencyPair.BTC_USD.counterSymbol);

    assertEquals("BTC", CurrencyPair.BTC_GBP.baseSymbol);
    assertEquals("USD", CurrencyPair.BTC_USD.counterSymbol);

    assertEquals("BTC", CurrencyPair.BTC_EUR.baseSymbol);
    assertEquals("EUR", CurrencyPair.BTC_EUR.counterSymbol);

    assertEquals("BTC", CurrencyPair.BTC_JPY.baseSymbol);
    assertEquals("JPY", CurrencyPair.BTC_JPY.counterSymbol);

    assertEquals("BTC", CurrencyPair.BTC_CHF.baseSymbol);
    assertEquals("CHF", CurrencyPair.BTC_CHF.counterSymbol);

    assertEquals("BTC", CurrencyPair.BTC_AUD.baseSymbol);
    assertEquals("AUD", CurrencyPair.BTC_AUD.counterSymbol);

    assertEquals("BTC", CurrencyPair.BTC_CAD.baseSymbol);
    assertEquals("CAD", CurrencyPair.BTC_CAD.counterSymbol);

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
        new CurrencyPair(testCases[i][0],testCases[i][1]);
        fail("Expected exception for "+testCases[i][0]+" "+testCases[i][1]);
      } catch (IllegalArgumentException e) {
        // Do nothing
      }

    }
  }
}
