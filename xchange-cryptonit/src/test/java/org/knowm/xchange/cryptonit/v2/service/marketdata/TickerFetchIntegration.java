package org.knowm.xchange.cryptonit.v2.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cryptonit.v2.CryptonitExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author timmolter */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CryptonitExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "LTC"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
