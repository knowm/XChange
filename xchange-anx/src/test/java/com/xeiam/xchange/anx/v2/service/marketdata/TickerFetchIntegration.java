package com.xeiam.xchange.anx.v2.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.anx.v2.ANXExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author timmolter
 */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(ANXExchange.class.getName());
    exchange.remoteInit();
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

}
