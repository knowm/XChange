package org.knowm.xchange.bibox.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bibox.BiboxExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class MarketDataServiceIntegration {

  private static final Exchange BIBOX = ExchangeFactory.INSTANCE.createExchange(BiboxExchange.class.getName());
  private static final CurrencyPair BIX_BTC = new CurrencyPair("BIX", "BTC");

  @Test
  public void testGetTicker() throws Exception {

    MarketDataService marketDataService = BIBOX.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(BIX_BTC);
    assertThat(ticker.getAsk()).isGreaterThan(ticker.getBid());
    assertThat(ticker.getHigh()).isGreaterThan(ticker.getLow());
  }
}
