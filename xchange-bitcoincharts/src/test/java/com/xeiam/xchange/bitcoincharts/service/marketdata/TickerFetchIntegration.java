package com.xeiam.xchange.bitcoincharts.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoincharts.BitcoinChartsExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author timmolter
 */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoinChartsExchange.class.getName());
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    exchange.remoteInit();
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "bitstampUSD"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

}
