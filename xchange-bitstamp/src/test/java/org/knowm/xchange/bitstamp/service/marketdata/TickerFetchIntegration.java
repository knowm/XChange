package org.knowm.xchange.bitstamp.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author timmolter */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {
    ExchangeSpecification spec = new ExchangeSpecification(BitstampExchange.class);
    spec.setProxyHost("localhost");
    spec.setProxyPort(1080);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);
//    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USD"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
