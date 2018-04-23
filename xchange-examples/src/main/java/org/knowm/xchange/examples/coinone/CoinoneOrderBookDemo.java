package org.knowm.xchange.examples.coinone;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinone.CoinoneExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting Ticker at CoinEgg */
public class CoinoneOrderBookDemo {

  public static void main(String[] args) throws IOException {

    // Create Default BitZ Instance
    Exchange coinone = ExchangeFactory.INSTANCE.createExchange(CoinoneExchange.class.getName());

    // Get The Public Market Data Service
    MarketDataService marketDataService = coinone.getMarketDataService();

    // Currency Pair To Get Ticker Of
    org.knowm.xchange.currency.CurrencyPair pair =
        org.knowm.xchange.currency.CurrencyPair.build(Currency.ETH, Currency.KRW);

    // Print The Generic and Raw Ticker
    System.out.println(marketDataService.getOrderBook(pair));
  }
}
