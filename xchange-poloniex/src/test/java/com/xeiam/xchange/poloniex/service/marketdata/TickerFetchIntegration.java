package com.xeiam.xchange.poloniex.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.poloniex.PoloniexExchange;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author timmolter
 */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
    exchange.remoteInit();
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();
    CurrencyPair currencyPair = exchange.getMetaData().getMarketMetaDataMap().keySet().iterator().next();
    Ticker ticker = marketDataService.getTicker(currencyPair);
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

}
