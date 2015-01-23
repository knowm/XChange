package com.xeiam.xchange.examples.btce.marketdata;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.btce.v3.BTCEExchange;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETrade;
import com.xeiam.xchange.btce.v3.service.polling.BTCEMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Order Book at BTC-E
 */
public class BTCETradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BTC-E exchange API using default settings
    Exchange btce = ExchangeFactory.INSTANCE.createExchange(BTCEExchange.class.getName());
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

    // Get the latest trade data for BTC/EUR
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_EUR);

    System.out.println(trades.toString());
  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    BTCEMarketDataServiceRaw marketDataService = (BTCEMarketDataServiceRaw) exchange.getPollingMarketDataService();

    // Get the latest trade data for BTC/USD
    Map<String, BTCETrade[]> trades = marketDataService.getBTCETrades("btc_usd", 7).getTradesMap();

    for (Map.Entry<String, BTCETrade[]> entry : trades.entrySet()) {
      System.out.println("Pair: " + entry.getKey() + ", Trades:");
      for (BTCETrade trade : entry.getValue()) {
        System.out.println(trade.toString());
      }
    }
  }

}
