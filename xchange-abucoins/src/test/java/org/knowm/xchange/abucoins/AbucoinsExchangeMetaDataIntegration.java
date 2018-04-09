package org.knowm.xchange.abucoins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

public class AbucoinsExchangeMetaDataIntegration {
  AbucoinsExchange exchange;

  @Before
  public void setUp() throws Exception {
    exchange = (AbucoinsExchange) ExchangeFactory.INSTANCE.createExchange(AbucoinsExchange.class);
  }

  @Test
  public void testCurrencyPairs() {
    ExchangeMetaData metaData = exchange.getExchangeMetaData();
    assertNotNull("meta data is null", metaData);

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = metaData.getCurrencyPairs();

    assertNotNull("currencyPairs meta data is null", currencyPairs);
    assertEquals("Wrong number of currency pairs", 31, currencyPairs.size());
  }

  @Test
  public void testCurrencies() {
    ExchangeMetaData metaData = exchange.getExchangeMetaData();
    assertNotNull("meta data is null", metaData);

    Map<Currency, CurrencyMetaData> currencies = metaData.getCurrencies();

    assertNotNull("currencies meta data is null", currencies);
    assertEquals("Wrong number of currencies", 20, currencies.size());
  }
}
