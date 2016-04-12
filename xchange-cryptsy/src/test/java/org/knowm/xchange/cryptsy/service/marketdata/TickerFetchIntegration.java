package org.knowm.xchange.cryptsy.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cryptsy.CryptsyExchange;
import org.knowm.xchange.cryptsy.service.polling.CryptsyPublicMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

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
