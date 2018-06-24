package org.knowm.xchange.examples.coinegg;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.xchange.coinegg.CoinEggExchange;

/** Demonstrate requesting Ticker at CoinEgg */
public class CoinEggTickerDemo {

  public static void main(String[] args) throws IOException {

    // Create Default BitZ Instance
    Exchange coinEgg = ExchangeFactory.INSTANCE.createExchange(CoinEggExchange.class.getName());

    // Get The Public Market Data Service
    MarketDataService marketDataService = coinEgg.getMarketDataService();

    // Currency Pair To Get Ticker Of
    CurrencyPair pair = CurrencyPair.ETH_BTC;

    // Print The Generic and Raw Ticker
    System.out.println(marketDataService.getTicker(pair));
  }
}
