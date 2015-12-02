package com.xeiam.xchange.examples.cexio.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.cexio.CexIOExchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Author: brox Since: 2/6/14
 */

public class TickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Cex.IO exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CexIOExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

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
