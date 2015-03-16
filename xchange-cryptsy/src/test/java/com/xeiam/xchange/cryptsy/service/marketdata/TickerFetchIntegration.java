package com.xeiam.xchange.cryptsy.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.cryptsy.CryptsyExchange;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyPublicMarketDataService;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;

/**
 * @author timmolter
 */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CryptsyExchange.class.getName());
    CryptsyPublicMarketDataService publicMarketDataService = (CryptsyPublicMarketDataService) ((CryptsyExchange) exchange)
        .getPollingPublicMarketDataService();

    Ticker ticker = publicMarketDataService.getTicker(new CurrencyPair("DOGE", "BTC"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

}
