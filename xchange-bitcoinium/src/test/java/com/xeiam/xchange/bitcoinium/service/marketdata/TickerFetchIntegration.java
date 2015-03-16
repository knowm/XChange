package com.xeiam.xchange.bitcoinium.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinium.BitcoiniumExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author timmolter
 */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoiniumExchange.class.getName());
    exchangeSpecification.setApiKey("42djci5kmbtyzrvglfdw3e2dgmh5mr37");
    exchangeSpecification.setPlainTextUri("http://bitcoinium.com");
    System.out.println(exchangeSpecification.toString());
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "BITSTAMP_USD"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

}
