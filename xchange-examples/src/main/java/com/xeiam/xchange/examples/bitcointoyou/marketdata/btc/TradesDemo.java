package com.xeiam.xchange.examples.bitcointoyou.marketdata.btc;

import java.io.IOException;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcointoyou.BitcoinToYouExchange;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouTransaction;
import com.xeiam.xchange.bitcointoyou.service.polling.BitcoinToYouMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Trades at BitcoinToYou
 * 
 * @author Copied from Bitstamp and adapted by Felipe Micaroni Lalli
 */
public class TradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BitcoinToYou exchange API using default settings
    Exchange bitcoinToYou = ExchangeFactory.INSTANCE.createExchange(BitcoinToYouExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitcoinToYou.getPollingMarketDataService();

    Long now = new Date().getTime();

    generic(now, marketDataService);
    raw(now, (BitcoinToYouMarketDataServiceRaw) marketDataService);

  }

  private static void generic(Long now, PollingMarketDataService marketDataService) throws IOException {

    // Get the latest trade data for BTC/BRL
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_BRL);
    System.out.println("Trades, default. Size= " + trades.getTrades().size());

    trades = marketDataService.getTrades(CurrencyPair.BTC_BRL, now - (24L * 60L * 60L * 1000L));
    System.out.println("Trades, last 24h= " + trades.getTrades().size());
  }

  private static void raw(Long now, BitcoinToYouMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest trade data for BTC/BRL
    BitcoinToYouTransaction[] trades = marketDataService.getBitcoinToYouTransactions(CurrencyPair.BTC_BRL);
    System.out.println("Trades, default. Size= " + trades.length);

    trades = marketDataService.getBitcoinToYouTransactions(CurrencyPair.BTC_BRL, now - (24L * 60L * 60L * 1000L));
    System.out.println("Trades, last 24h= " + trades.length);
  }
}
