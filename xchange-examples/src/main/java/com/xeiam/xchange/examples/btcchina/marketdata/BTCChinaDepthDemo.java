package com.xeiam.xchange.examples.btcchina.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.btcchina.BTCChinaExchange;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author ObsessiveOrange Demonstrate requesting Order Book at BTC China
 */
public class BTCChinaDepthDemo {

  // Use the factory to get the BTCChina exchange API using default settings
  static Exchange btcchina = ExchangeFactory.INSTANCE.createExchange(BTCChinaExchange.class.getName());

  // Interested in the public polling market data feed (no authentication)
  static PollingMarketDataService marketDataService = btcchina.getPollingMarketDataService();

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
