package org.knowm.xchange.examples.yobit.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.yobit.YoBitExchange;

/**
 * Demonstrate requesting OrderBook from YoBit.
 */
public class YoBitBookDemo {

  public static void main(String[] args) throws IOException {

    Exchange yoBitExchange = ExchangeFactory.INSTANCE.createExchange(YoBitExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = yoBitExchange.getMarketDataService();

    System.out.println("fetching data...");

    // Get the current orderbook
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_BTC);

    System.out.println("received data.");

    for (LimitOrder limitOrder : orderBook.getBids()) {
      System.out.println(limitOrder.getType() + " " + limitOrder.getCurrencyPair() + " Limit price: " + limitOrder.getLimitPrice() + " Amount: "
          + limitOrder.getTradableAmount());
    }

    for (LimitOrder limitOrder : orderBook.getAsks()) {
      System.out.println(limitOrder.getType() + " " + limitOrder.getCurrencyPair() + " Limit price: " + limitOrder.getLimitPrice() + " Amount: "
          + limitOrder.getTradableAmount());
    }
  }

}
