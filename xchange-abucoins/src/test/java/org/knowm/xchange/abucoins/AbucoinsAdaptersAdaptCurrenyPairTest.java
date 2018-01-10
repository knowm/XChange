package org.knowm.xchange.abucoins;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

public class AbucoinsAdaptersAdaptCurrenyPairTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testAdaptsAbucoinsProductIDToCurrencyPair() {
    assertEquals("Not adapting", CurrencyPair.BTC_USD, AbucoinsAdapters.adaptCurrencyPair("BTC-USD"));
  }

}
