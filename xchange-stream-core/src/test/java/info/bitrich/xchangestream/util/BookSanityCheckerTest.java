package info.bitrich.xchangestream.util;

import static java.lang.String.format;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BookSanityCheckerTest {

  @Test
  public void testNoOrders() {
    OrderBook book =
        new OrderBook(new Date(), new ArrayList<LimitOrder>(), new ArrayList<LimitOrder>());
    Assert.assertNull(BookSanityChecker.hasErrors(book));
  }

  @Test
  public void testWithAsksWithBidsNoErrors() {
    ArrayList<LimitOrder> asks = new ArrayList<>();
    ArrayList<LimitOrder> bids = new ArrayList<>();
    asks.add(
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal(0.02),
            CurrencyPair.ADA_BNB,
            "1",
            new Date(),
            new BigDecimal(0.02)));
    bids.add(
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal(0.01),
            CurrencyPair.ADA_BNB,
            "2",
            new Date(),
            new BigDecimal(0.01)));
    OrderBook book = new OrderBook(new Date(), asks, bids);
    Assert.assertNull(BookSanityChecker.hasErrors(book));
  }

  @Test
  public void testNoBidsNoErrors() {
    ArrayList<LimitOrder> asks = new ArrayList<>();
    ArrayList<LimitOrder> bids = new ArrayList<>();
    asks.add(
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal(0.01),
            CurrencyPair.ADA_BNB,
            "1",
            new Date(),
            new BigDecimal(0.01)));
    asks.add(
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal(0.02),
            CurrencyPair.ADA_BNB,
            "2",
            new Date(),
            new BigDecimal(0.02)));
    OrderBook book = new OrderBook(new Date(), asks, bids);
    Assert.assertNull(BookSanityChecker.hasErrors(book));
  }

  @Test
  public void testWithAsksLimitOrderError() {
    ArrayList<LimitOrder> asks = new ArrayList<>();
    ArrayList<LimitOrder> bids = new ArrayList<>();
    LimitOrder a1 =
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal(-0.01),
            CurrencyPair.ADA_BNB,
            "1",
            new Date(),
            new BigDecimal(0.01));
    asks.add(a1);
    OrderBook book = new OrderBook(new Date(), asks, bids);
    Assert.assertEquals(
        format("LimitOrder amount is <= 0 for %s", a1), BookSanityChecker.hasErrors(book));
  }

  @Test
  public void testWithBidsLimitOrderError() {
    ArrayList<LimitOrder> asks = new ArrayList<>();
    ArrayList<LimitOrder> bids = new ArrayList<>();
    LimitOrder b1 =
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal(-0.01),
            CurrencyPair.ADA_BNB,
            "1",
            new Date(),
            new BigDecimal(0.01));
    bids.add(b1);
    OrderBook book = new OrderBook(new Date(), asks, bids);
    Assert.assertEquals(
        format("LimitOrder amount is <= 0 for %s", b1), BookSanityChecker.hasErrors(book));
  }

  @Test
  public void testWithBidNoErrorOnNextOrder() {
    ArrayList<LimitOrder> asks = new ArrayList<>();
    ArrayList<LimitOrder> bids = new ArrayList<>();
    LimitOrder b1 =
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal(-0.01),
            CurrencyPair.ADA_BNB,
            "1",
            new Date(),
            new BigDecimal(0.01));
    LimitOrder b2 =
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal(0.01),
            CurrencyPair.ADA_BNB,
            "2",
            new Date(),
            new BigDecimal(0.01));
    bids.add(b1);
    bids.add(b2);
    OrderBook book = new OrderBook(new Date(), asks, bids);
    Assert.assertEquals(
        format("LimitOrder amount is <= 0 for %s", b1), BookSanityChecker.hasErrors(book));
  }

  @Test
  public void testIncorrectBestAskAndBid() {
    ArrayList<LimitOrder> asks = new ArrayList<>();
    ArrayList<LimitOrder> bids = new ArrayList<>();
    LimitOrder a1 =
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal(0.01),
            CurrencyPair.ADA_BNB,
            "1",
            new Date(),
            new BigDecimal(0.01));
    asks.add(a1);
    LimitOrder b1 =
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal(0.02),
            CurrencyPair.ADA_BNB,
            "2",
            new Date(),
            new BigDecimal(0.02));
    bids.add(b1);
    OrderBook book = new OrderBook(new Date(), asks, bids);
    Assert.assertEquals(
        format("Got incorrect best ask and bid %s, %s", a1, b1), BookSanityChecker.hasErrors(book));
  }

  @Test
  public void testWithBidsWrongPriceOrder() {
    ArrayList<LimitOrder> asks = new ArrayList<>();
    ArrayList<LimitOrder> bids = new ArrayList<>();
    LimitOrder b1 =
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal(0.01),
            CurrencyPair.ADA_BNB,
            "1",
            new Date(),
            new BigDecimal(0.01));
    LimitOrder b2 =
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal(0.02),
            CurrencyPair.ADA_BNB,
            "2",
            new Date(),
            new BigDecimal(0.02));
    bids.add(b1);
    bids.add(b2);
    OrderBook book = new OrderBook(new Date(), asks, bids);
    Assert.assertEquals(
        format("Wrong price order for LimitOrders %s, %s", b2, b1),
        BookSanityChecker.hasErrors(book));
  }

  @Test
  public void testNoNextOrder() {
    ArrayList<LimitOrder> limitOrders = new ArrayList<>();
    Assert.assertNull(BookSanityChecker.hasErrors(limitOrders.iterator()));
  }
}
