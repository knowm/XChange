package org.knowm.xchange.btcturk.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btcturk.BTCTurkExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

/**
 * @author semihunaldi
 * @author mertguner
 */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BTCTurkExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "TRY"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();

    List<Ticker> tickers = marketDataService.getTickers(new Params() {});
    for (Ticker _ticker : tickers) {
      System.out.println(_ticker.toString());
      assertThat(_ticker).isNotNull();
    }
  }
}
