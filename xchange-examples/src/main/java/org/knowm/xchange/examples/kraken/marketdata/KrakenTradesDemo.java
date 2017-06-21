package org.knowm.xchange.examples.kraken.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicTrades;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class KrakenTradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Kraken exchange API using default settings
    Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

    generic(krakenExchange);
//    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = krakenExchange.getMarketDataService();

    // Get the latest trade data for BTC_USD
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);
    System.out.println(trades);
    System.out.println("Trades(0): " + trades.getTrades().get(0).toString());
    System.out.println("Trades size: " + trades.getTrades().size());

    // Get the latest trade data for BTC_USD for the past 12 hours (note:
    // doesn't account for time zone differences, should use UTC instead)
//    trades = marketDataService.getTrades(CurrencyPair.BTC_USD, (long) (System.nanoTime() - (12 * 60 * 60 * Math.pow(10, 9))));
//    System.out.println(trades);
//    System.out.println("Trades size: " + trades.getTrades().size());
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    KrakenMarketDataServiceRaw krakenMarketDataService = (KrakenMarketDataServiceRaw) krakenExchange.getMarketDataService();

    // Get the latest trade data for BTC_USD
    KrakenPublicTrades krakenPublicTrades = krakenMarketDataService.getKrakenTrades(CurrencyPair.BTC_USD);
    long last = krakenPublicTrades.getLast();
    System.out.println(krakenPublicTrades.getTrades());

    System.out.println("Trades size: " + krakenPublicTrades.getTrades().size());
    System.out.println("Trades(0): " + krakenPublicTrades.getTrades().get(0).toString());
    System.out.println("Last: " + last);

    // Poll for any new trades since last id
    krakenPublicTrades = krakenMarketDataService.getKrakenTrades(CurrencyPair.LTC_USD, last);
    System.out.println(krakenPublicTrades.getTrades());

    System.out.println("Trades size: " + krakenPublicTrades.getTrades().size());

  }
}
