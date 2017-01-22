package org.knowm.xchange.examples.chbtc;

import java.io.IOException;
import java.util.Set;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.chbtc.ChbtcExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class ChbtcMarketDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(ChbtcExchange.class.getName());

    MarketDataService marketDataService = exchange.getMarketDataService();

    // BTC/CNY and ETH/CNY
    Set<CurrencyPair> currencyPairs = exchange.getExchangeMetaData().getCurrencyPairs().keySet();

    for (CurrencyPair currencyPair : currencyPairs) {
      System.out.println("#### Currency pair " + currencyPair);

      OrderBook orderBook = marketDataService.getOrderBook(currencyPair);

      System.out.println("Current Order Book size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));
      System.out.println("First Ask: " + orderBook.getAsks().get(0));
      System.out.println("First Bid: " + orderBook.getBids().get(0));

      Ticker ticker = marketDataService.getTicker(currencyPair);
      System.out.println("Ticker: " + ticker);

      Trades trades = marketDataService.getTrades(currencyPair);
      System.out.println("Last 3 trades: " + trades.getTrades().subList(0, 3));
    }
  }
}
