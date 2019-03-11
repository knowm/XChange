package org.knowm.xchange.liqui.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.liqui.LiquiExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(LiquiExchange.class.getName());
    final MarketDataService marketDataService = exchange.getMarketDataService();
    final Ticker ticker = marketDataService.getTicker(new CurrencyPair("LTC", "BTC"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
