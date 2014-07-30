package com.xeiam.xchange.examples.kraken.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.kraken.KrakenExchange;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenPublicTrades;
import com.xeiam.xchange.kraken.service.polling.KrakenMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class KrakenTradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Kraken exchange API using default settings
    Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = krakenExchange.getPollingMarketDataService();

    // Get the latest trade data for BTC_USD
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);
    System.out.println(trades);
    System.out.println("Trades(0): " + trades.getTrades().get(0).toString());
    System.out.println("Trades size: " + trades.getTrades().size());

    // Get the latest trade data for BTC_USD for the past 12 hours (note: doesn't account for time zone differences, should use UTC instead)
    trades = marketDataService.getTrades(CurrencyPair.BTC_USD, (long) (System.nanoTime() - (12 * 60 * 60 * Math.pow(10, 9))));
    System.out.println(trades);
    System.out.println("Trades size: " + trades.getTrades().size());
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    KrakenMarketDataServiceRaw krakenMarketDataService = (KrakenMarketDataServiceRaw) krakenExchange.getPollingMarketDataService();

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
