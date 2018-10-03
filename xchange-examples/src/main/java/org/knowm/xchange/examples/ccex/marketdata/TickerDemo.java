package org.knowm.xchange.examples.ccex.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ccex.CCEXExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting Ticker from C-CEX. */
public class TickerDemo {

  public static void main(String[] args) throws IOException {

    Exchange ccexExchange = ExchangeFactory.INSTANCE.createExchange(CCEXExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = ccexExchange.getMarketDataService();

    System.out.println("fetching data...");

    // Get the current orderbook
    Ticker ticker = marketDataService.getTicker(CurrencyPair.XAUR_BTC);

    System.out.println("received data.");

    System.out.println(ticker);
  }
}
