package org.knowm.xchange.examples.wex.marketdata;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.wex.v3.WexExchange;
import org.knowm.xchange.wex.v3.dto.marketdata.WexTrade;
import org.knowm.xchange.wex.v3.service.WexMarketDataServiceRaw;

/** Demonstrate requesting Order Book at BTC-E */
public class WexTradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BTC-E exchange API using default settings
    Exchange btce = ExchangeFactory.INSTANCE.createExchange(WexExchange.class.getName());
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = exchange.getMarketDataService();

    // Get the latest trade data for BTC/EUR
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_EUR);

    System.out.println(trades.toString());
  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    WexMarketDataServiceRaw marketDataService =
        (WexMarketDataServiceRaw) exchange.getMarketDataService();

    // Get the latest trade data for BTC/USD
    Map<String, WexTrade[]> trades = marketDataService.getBTCETrades("btc_usd", 7).getTradesMap();

    for (Map.Entry<String, WexTrade[]> entry : trades.entrySet()) {
      System.out.println("Pair: " + entry.getKey() + ", Trades:");
      for (WexTrade trade : entry.getValue()) {
        System.out.println(trade.toString());
      }
    }
  }
}
