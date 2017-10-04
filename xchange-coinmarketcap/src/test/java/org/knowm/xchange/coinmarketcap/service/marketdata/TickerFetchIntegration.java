package org.knowm.xchange.coinmarketcap.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinmarketcap.CoinMarketCapExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.util.List;

/**
 * @author allenday
 */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinMarketCapExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();

    CurrencyPair NEO_BTC = new CurrencyPair("NEO", "BTC");
    Ticker ticker0 = marketDataService.getTicker(NEO_BTC);
    //System.out.println(ticker.toString());
    assertThat(ticker0).isNotNull();

    Boolean found = false;
    List<CurrencyPair> pairs = exchange.getExchangeSymbols();
    for (CurrencyPair p : pairs) {
      if (p.compareTo(NEO_BTC) == 0) {
        found = true;
      }
    }
    assert(found);
  }
}
