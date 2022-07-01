package org.knowm.xchange.paribu.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.paribu.ParibuExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author semihunaldi */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(ParibuExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "TRY"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
