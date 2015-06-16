package com.xeiam.xchange.examples.cointrader;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.cointrader.Cointrader;
import com.xeiam.xchange.cointrader.CointraderExchange;
import com.xeiam.xchange.cointrader.dto.marketdata.CointraderOrderBook;
import com.xeiam.xchange.cointrader.dto.marketdata.CointraderTicker;
import com.xeiam.xchange.cointrader.service.polling.CointraderMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class CointraderMarketDataDemo {

  public static void main(String[] args) throws IOException {
    // Use the factory to get Cointrader exchange API using default settings
    Exchange cointraderExchange = ExchangeFactory.INSTANCE.createExchange(CointraderExchange.class.getName());
    generic(cointraderExchange);
    raw(cointraderExchange);
  }

  private static void generic(Exchange cointraderExchange) throws IOException {
    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService cointraderMarketDataService = cointraderExchange.getPollingMarketDataService();

    // Get the (daily) ticker
    System.out.println("Ticker: " + cointraderMarketDataService.getTicker(CurrencyPair.BTC_USD));

    OrderBook orderBook = cointraderMarketDataService.getOrderBook(CurrencyPair.BTC_USD);
    System.out.println(orderBook.toString());
    System.out.println("full orderbook size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    orderBook = cointraderMarketDataService.getOrderBook(CurrencyPair.BTC_USD, Cointrader.OrderBookType.all, 10);
    System.out.println(orderBook.toString());
    System.out.println("partial orderbook asks: " + (orderBook.getAsks().size()));
    System.out.println("partial orderbook bids: " + (orderBook.getBids().size()));
  }

  private static void raw(Exchange cointraderExchange) throws IOException {
    // Interested in the public polling market data feed (no authentication)
    CointraderMarketDataServiceRaw cointraderMarketDataService = (CointraderMarketDataServiceRaw) cointraderExchange.getPollingMarketDataService();

    // Get the weekly ticker
    System.out.println("Ticker: " + cointraderMarketDataService.getCointraderTicker(new Cointrader.Pair(CurrencyPair.BTC_USD), CointraderTicker.Type.weekly));

    // Get the latest full order book data
    CointraderOrderBook depth = cointraderMarketDataService.getCointraderOrderBook(new Cointrader.Pair(CurrencyPair.BTC_USD), 10, Cointrader.OrderBookType.all);
    System.out.println(depth.toString());
    System.out.println("offers: " + (depth.getData().size()));
  }
}
