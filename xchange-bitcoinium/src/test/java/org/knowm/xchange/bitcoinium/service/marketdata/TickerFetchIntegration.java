package org.knowm.xchange.bitcoinium.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoinium.BitcoiniumExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author timmolter */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BitcoiniumExchange.class.getName());
    exchangeSpecification.setApiKey("42djci5kmbtyzrvglfdw3e2dgmh5mr37");
    exchangeSpecification.setPlainTextUri("http://bitcoinium.com");
    System.out.println(exchangeSpecification.toString());
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "BITSTAMP_USD"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
