package org.knowm.xchange.gdax.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gdax.GDAXExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Created by Yingzhe on 4/12/2015. */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USD"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
