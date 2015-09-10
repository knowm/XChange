package com.xeiam.xchange.jubi.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.jubi.JubiExchange;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Created by Yingzhe on 3/17/2015.
 */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(JubiExchange.class.getName());
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "CNY"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
