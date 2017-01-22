package org.knowm.xchange.examples.btcchina.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import org.knowm.xchange.btcchina.service.rest.BTCChinaMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author ObsessiveOrange Demonstrate requesting Order Book at BTC China
 */
public class BTCChinaDepthDemo {

  // Use the factory to get the BTCChina exchange API using default settings
  static Exchange btcchina = ExchangeFactory.INSTANCE.createExchange(BTCChinaExchange.class.getName());

  // Interested in the public market data feed (no authentication)
  static MarketDataService marketDataService = btcchina.getMarketDataService();

  public static void main(String[] args) throws IOException {

    generic();
    raw();
  }

  public static void generic() throws IOException {

    // Get the latest order book data for BTC/CNY
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_CNY, 10);

    // System.out.println(orderBook.toString());
    System.out.println("Date: " + orderBook.getTimeStamp());

    List<LimitOrder> asks = orderBook.getAsks();
    List<LimitOrder> bids = orderBook.getBids();

    System.out.println("lowestAsk: " + asks.get(0));
    System.out.println("asks Size: " + asks.size());
    System.out.println("highestBid: " + bids.get(0));
    System.out.println("bids Size: " + bids.size());

    if (asks.size() >= 2) {
      boolean lower = asks.get(0).getLimitPrice().compareTo(asks.get(1).getLimitPrice()) < 0;
      assert lower : "asks should be sorted ascending";
    }
    if (bids.size() >= 2) {
      boolean higher = bids.get(0).getLimitPrice().compareTo(bids.get(1).getLimitPrice()) > 0;
      assert higher : "bids should be sorted descending";
    }
  }

  public static void raw() throws IOException {

    // Get the latest order book data for BTC/CNY
    BTCChinaDepth orderBook = ((BTCChinaMarketDataServiceRaw) marketDataService).getBTCChinaOrderBook(BTCChinaExchange.DEFAULT_MARKET, 10);

    System.out.println("Date: " + orderBook.getDate());
    System.out.println(orderBook.toString());

    // first item in each BigDecial[] will be price (in RMB), and second will be volume/depth.
    System.out.println("Asks:");
    for (BigDecimal[] currRow : orderBook.getAsksArray()) {
      for (BigDecimal currItem : currRow) {
        System.out.print(currItem.toPlainString() + ", ");
      }
      System.out.println();
    }

    System.out.println("Bids:");
    for (BigDecimal[] currRow : orderBook.getBidsArray()) {
      for (BigDecimal currItem : currRow) {
        System.out.print(currItem.toPlainString() + ", ");
      }
      System.out.println();
    }
  }
}
