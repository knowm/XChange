package com.xeiam.xchange.cryptotrade.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Ignore;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.cryptotrade.CryptoTradeExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author timmolter
 */
public class TickerFetchIntegration {

  // TODO activate this when the exchange is live again
  @Ignore
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CryptoTradeExchange.class.getName());
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("DOGE", "BTC"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

}
