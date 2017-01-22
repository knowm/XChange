package org.knowm.xchange.jubi.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.jubi.JubiExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Created by Yingzhe on 3/17/2015.
 */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(JubiExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "CNY"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
