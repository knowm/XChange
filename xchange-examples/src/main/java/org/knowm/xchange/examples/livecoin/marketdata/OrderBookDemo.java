package org.knowm.xchange.examples.livecoin.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting OrderBook from Livecoin. */
public class OrderBookDemo {

  public static void main(String[] args) throws IOException {

    Exchange livecoinExchange =
        ExchangeFactory.INSTANCE.createExchange(LivecoinExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = livecoinExchange.getMarketDataService();

    System.out.println("fetching data...");

    // Get the current orderbook
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);

    System.out.println("received data.");

    for (LimitOrder limitOrder : orderBook.getBids()) {
      System.out.println(
          limitOrder.getType()
              + " "
              + limitOrder.getCurrencyPair()
              + " Limit price: "
              + limitOrder.getLimitPrice()
              + " Amount: "
              + limitOrder.getOriginalAmount());
    }

    for (LimitOrder limitOrder : orderBook.getAsks()) {
      System.out.println(
          limitOrder.getType()
              + " "
              + limitOrder.getCurrencyPair()
              + " Limit price: "
              + limitOrder.getLimitPrice()
              + " Amount: "
              + limitOrder.getOriginalAmount());
    }
  }
}
