package org.knowm.xchange.examples.upbit.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.upbit.UpbitExchange;
import org.xchange.coinegg.CoinEggExchange;

/** Demonstrate requesting Ticker at Upbit */
public class UpbitTickerDemo {

  public static void main(String[] args) throws IOException {

    // Create Default Upbit Instance
    Exchange upbit = ExchangeFactory.INSTANCE.createExchange(UpbitExchange.class.getName());
    

    // Get The Public Market Data Service
    MarketDataService marketDataService = upbit.getMarketDataService();

    // Currency Pair To Get Ticker Of
    CurrencyPair pair = new CurrencyPair(Currency.ETH, Currency.KRW);

    // Print The Generic and Raw Ticker
    System.out.println(marketDataService.getTicker(pair));
  }
}
