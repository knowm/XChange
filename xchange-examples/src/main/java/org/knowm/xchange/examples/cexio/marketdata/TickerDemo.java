package org.knowm.xchange.examples.cexio.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cexio.CexIOExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Author: brox Since: 2/6/14
 */

public class TickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Cex.IO exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CexIOExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = exchange.getMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = marketDataService.getTicker(new CurrencyPair(Currency.GHs, Currency.BTC));

    System.out.println("Pair: " + Currency.GHs + "/" + Currency.BTC);
    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Volume: " + ticker.getVolume().toString());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());
    System.out.println("Bid: " + ticker.getBid().toString());
    System.out.println("Ask: " + ticker.getAsk().toString());
    System.out.println("Timestamp: " + ticker.getTimestamp().toString());
  }

}
