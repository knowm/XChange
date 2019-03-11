package org.knowm.xchange.bitcoincharts.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoincharts.BitcoinChartsExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author timmolter */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BitcoinChartsExchange.class.getName());
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    exchange.remoteInit();
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "bitstampUSD"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
