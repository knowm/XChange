package org.knowm.xchange.examples.mercadobitcoin.marketdata.btc;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinExchange;
import org.knowm.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinTransaction;
import org.knowm.xchange.mercadobitcoin.service.MercadoBitcoinMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Demonstrate requesting Trades at Mercado Bitcoin
 *
 * @author Copied from Bitstamp and adapted by Felipe Micaroni Lalli
 */
public class TradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Mercado Bitcoin exchange API using default settings
    Exchange mercadoBitcoin = ExchangeFactory.INSTANCE.createExchange(MercadoBitcoinExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = mercadoBitcoin.getMarketDataService();

    Long now = new Date().getTime();

    generic(now, marketDataService);
    raw(now, (MercadoBitcoinMarketDataServiceRaw) marketDataService);

  }

  private static void generic(Long now, MarketDataService marketDataService) throws IOException {

    // Get the latest trade data for BTC/BRL
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_BRL);
    System.out.println("Trades, default. Size= " + trades.getTrades().size());

    trades = marketDataService.getTrades(CurrencyPair.BTC_BRL, now - (24L * 60L * 60L * 1000L));
    System.out.println("Trades, last 24h= " + trades.getTrades().size());

    trades = marketDataService.getTrades(CurrencyPair.BTC_BRL, 1406851200000L, 1409529600000L);
    System.out.println("Trades, since Aug 2014 to Sep 2014= " + trades.getTrades().size());
    System.out.println(trades.toString());
  }

  private static void raw(Long now, MercadoBitcoinMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest trade data for BTC/BRL
    MercadoBitcoinTransaction[] trades = marketDataService.getMercadoBitcoinTransactions(CurrencyPair.BTC_BRL);
    System.out.println("Trades, default. Size= " + trades.length);

    trades = marketDataService.getMercadoBitcoinTransactions(CurrencyPair.BTC_BRL, now - (24L * 60L * 60L * 1000L));
    System.out.println("Trades, last 24h= " + trades.length);

    trades = marketDataService.getMercadoBitcoinTransactions(CurrencyPair.BTC_BRL, 1406851200000L, 1409529600000L);
    System.out.println("Trades, since Aug 2014 to Sep 2014= " + trades.length);
    System.out.println(Arrays.toString(trades));
  }
}
