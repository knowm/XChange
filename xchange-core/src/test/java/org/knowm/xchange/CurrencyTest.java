package org.knowm.xchange;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.knowm.xchange.currency.Currency;

public class CurrencyTest {

  @Test
  public void testCurrencyCode() {
    assertEquals(Currency.CNY.getCodeCurrency("CNY"), Currency.CNY);
    assertEquals(Currency.CNY.getCodeCurrency("cny"), Currency.CNY);
  }

  @Test
  public void testGetInstance() {
    assertEquals(Currency.BTC, Currency.getInstance("BTC"));
    assertEquals(Currency.BTC, Currency.getInstance("btc"));
    assertEquals(new Currency("btc"), Currency.getInstance("BTC"));
  }

  @Test
  public void testGetInstanceNoCreate() {
    assertEquals(Currency.CNY, Currency.getInstanceNoCreate("CNY"));
    assertEquals(Currency.CNY, Currency.getInstanceNoCreate("cny"));
    assertEquals(new Currency("cny"), Currency.getInstanceNoCreate("CNY"));
  }
}
