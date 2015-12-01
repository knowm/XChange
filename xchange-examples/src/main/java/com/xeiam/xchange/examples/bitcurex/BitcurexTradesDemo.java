package com.xeiam.xchange.examples.bitcurex;

import java.io.IOException;
import java.util.Arrays;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcurex.BitcurexExchange;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTrade;
import com.xeiam.xchange.bitcurex.service.polling.BitcurexMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Trades at Bitcurex
 */
public class BitcurexTradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the Bitcurex exchange API using default settings
    Exchange bitcurex = ExchangeFactory.INSTANCE.createExchange(BitcurexExchange.class.getName());
    requestData(bitcurex, CurrencyPair.BTC_EUR);
    requestData(bitcurex, CurrencyPair.BTC_PLN);
  }

  private static void requestData(Exchange bitcurex, CurrencyPair pair) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitcurex.getPollingMarketDataService();

    // // Get the latest trade data for BTC/EUR
    // Trades trades = marketDataService.getTrades(CurrencyPair.BTC_EUR);
    // System.out.println(trades.toString());

    generic(marketDataService, pair);
    raw((BitcurexMarketDataServiceRaw) marketDataService, pair.counter.getCurrencyCode());
  }

  private static void generic(PollingMarketDataService marketDataService, CurrencyPair pair) throws IOException {

    // Get the latest trade data for BTC/EUR
    Trades trades = marketDataService.getTrades(pair);
    System.out.println("Trades, Size= " + trades.getTrades().size());
    System.out.println(trades.toString());
  }

  private static void raw(BitcurexMarketDataServiceRaw marketDataService, String currency) throws IOException {

    // Get the latest trade data for BTC/EUR
    BitcurexTrade[] trades = marketDataService.getBitcurexTrades(currency);
    System.out.println("Trades, default. Size= " + trades.length);
    System.out.println(Arrays.toString(trades));
  }

}
