package org.knowm.xchange.examples.coinone.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinone.CoinoneExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting Ticker */
public class CoinoneTickerDemo {

  public static void main(String[] args) throws IOException {

    // Create Default BitZ Instance
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinoneExchange.class);

    // Get The Public Market Data Service
    MarketDataService marketDataService = exchange.getMarketDataService();

    // Currency Pair To Get Ticker Of
    CurrencyPair pair = new CurrencyPair(Currency.ETH, Currency.KRW);

    // Print The Generic and Raw Ticker
    System.out.println(marketDataService.getTicker(pair));
  }
}
