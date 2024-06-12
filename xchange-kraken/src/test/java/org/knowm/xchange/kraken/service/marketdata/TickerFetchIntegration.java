package org.knowm.xchange.kraken.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USD"));
    System.out.println(ticker.toString());
    List<Ticker> tickersList = marketDataService.getTickers(null);
    tickersList.forEach(System.out::println);
    assertThat(ticker).isNotNull();
    tickersList.forEach(
        ticker1 -> {
          assertThat(ticker1.getInstrument()).isNotNull();
          assertThat(ticker1.getCurrencyPair()).isNotNull();
        });
  }
}
