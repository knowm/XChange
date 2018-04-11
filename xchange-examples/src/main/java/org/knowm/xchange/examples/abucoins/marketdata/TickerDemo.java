package org.knowm.xchange.examples.abucoins.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.abucoins.AbucoinsExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Author: bryant_harris */
public class TickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Cex.IO exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(AbucoinsExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = exchange.getMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println("Pair: " + ticker.getCurrencyPair());
    System.out.println("Last: " + ticker.getLast());
    System.out.println("Volume: " + ticker.getVolume());
    System.out.println("High: " + ticker.getHigh());
    System.out.println("Low: " + ticker.getLow());
    System.out.println("Bid: " + ticker.getBid());
    System.out.println("Ask: " + ticker.getAsk());
    System.out.println("Timestamp: " + ticker.getTimestamp());
  }
}
