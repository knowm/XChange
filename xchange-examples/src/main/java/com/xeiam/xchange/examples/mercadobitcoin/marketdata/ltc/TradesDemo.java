package com.xeiam.xchange.examples.mercadobitcoin.marketdata.ltc;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.mercadobitcoin.MercadoBitcoinExchange;
import com.xeiam.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinTransaction;
import com.xeiam.xchange.mercadobitcoin.service.polling.MercadoBitcoinMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Trades at Mercado Bitcoin
 * 
 * @author Copied from Bitstamp and adapted by Felipe Micaroni Lalli
 */
public class TradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Mercado Bitcoin exchange API using default settings
    Exchange mercadoBitcoin = ExchangeFactory.INSTANCE.createExchange(MercadoBitcoinExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = mercadoBitcoin.getPollingMarketDataService();

    Long now = new Date().getTime();

    generic(now, marketDataService);
    raw(now, (MercadoBitcoinMarketDataServiceRaw) marketDataService);

  }

  private static void generic(Long now, PollingMarketDataService marketDataService) throws IOException {

    // Get the latest trade data for LTC/BRL
    Trades trades = marketDataService.getTrades(new CurrencyPair(Currency.LTC, Currency.BRL));
    System.out.println("Trades, default. Size= " + trades.getTrades().size());

    trades = marketDataService.getTrades(new CurrencyPair(Currency.LTC, Currency.BRL), now - (24L * 60L * 60L * 1000L));
    System.out.println("Trades, last 24h= " + trades.getTrades().size());

    trades = marketDataService.getTrades(new CurrencyPair(Currency.LTC, Currency.BRL), 1406851200000L, 1409529600000L);
    System.out.println("Trades, since Aug 2014 to Sep 2014= " + trades.getTrades().size());
    System.out.println(trades.toString());
  }

  private static void raw(Long now, MercadoBitcoinMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest trade data for LTC/BRL
    MercadoBitcoinTransaction[] trades = marketDataService.getMercadoBitcoinTransactions(new CurrencyPair(Currency.LTC, Currency.BRL));
    System.out.println("Trades, default. Size= " + trades.length);

    trades = marketDataService.getMercadoBitcoinTransactions(new CurrencyPair(Currency.LTC, Currency.BRL), now - (24L * 60L * 60L * 1000L));
    System.out.println("Trades, last 24h= " + trades.length);

    trades = marketDataService.getMercadoBitcoinTransactions(new CurrencyPair(Currency.LTC, Currency.BRL), 1406851200000L, 1409529600000L);
    System.out.println("Trades, since Aug 2014 to Sep 2014= " + trades.length);
    System.out.println(Arrays.toString(trades));
  }
}
