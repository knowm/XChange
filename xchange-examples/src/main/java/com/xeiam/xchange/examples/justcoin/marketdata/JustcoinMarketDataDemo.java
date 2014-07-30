package com.xeiam.xchange.examples.justcoin.marketdata;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.justcoin.JustcoinExchange;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinDepth;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinPublicTrade;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;
import com.xeiam.xchange.justcoin.service.polling.JustcoinMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class JustcoinMarketDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Justcoin exchange API using default settings
    Exchange justcoinExchange = ExchangeFactory.INSTANCE.createExchange(JustcoinExchange.class.getName());
    generic(justcoinExchange);
    raw(justcoinExchange);
  }

  private static void generic(Exchange justcoinExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService justcoinGenericMarketDataService = justcoinExchange.getPollingMarketDataService();

    // Get the latest ticker data for the BTC/LTC market
    Ticker ticker = justcoinGenericMarketDataService.getTicker(CurrencyPair.BTC_LTC);
    System.out.println(ticker);

    // Get the latest order book data for BTC/LTC
    OrderBook orderBook = justcoinGenericMarketDataService.getOrderBook(CurrencyPair.BTC_LTC);
    System.out.println("Order book: " + orderBook);

    Trades trades = justcoinGenericMarketDataService.getTrades(CurrencyPair.BTC_LTC, 92734);
    System.out.println(trades);
  }

  private static void raw(Exchange justcoinExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    JustcoinMarketDataServiceRaw justcoinSpecificMarketDataService = (JustcoinMarketDataServiceRaw) justcoinExchange.getPollingMarketDataService();

    Collection<CurrencyPair> currencyPairs = justcoinSpecificMarketDataService.getExchangeSymbols();
    System.out.println(currencyPairs);

    // Get the latest ticker data for all markets on the Justcoin Exchange
    List<JustcoinTicker> justcoinTickers = justcoinSpecificMarketDataService.getTickers();

    System.out.println(justcoinTickers);

    // Get the latest market depth data for BTC/LTC
    JustcoinDepth justcoinMarketDepth = justcoinSpecificMarketDataService.getMarketDepth(Currencies.BTC, Currencies.LTC);

    System.out.println(justcoinMarketDepth);

    List<JustcoinPublicTrade> trades = justcoinSpecificMarketDataService.getTrades(Currencies.LTC, 92734L);
    System.out.println(trades);
  }

}
