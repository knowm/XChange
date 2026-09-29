package info.bitrich.xchangestream.util;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

class BookSanityCheckerTest {

  @Test
  void noOrders() {
    OrderBook book =
        new OrderBook(new Date(), new ArrayList<LimitOrder>(), new ArrayList<LimitOrder>());
    assertThat(BookSanityChecker.hasErrors(book)).isNull();
  }

  @Test
  void withAsksWithBidsNoErrors() {
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
    assertThat(BookSanityChecker.hasErrors(book)).isNull();
  }

  @Test
  void noBidsNoErrors() {
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
    assertThat(BookSanityChecker.hasErrors(book)).isNull();
  }

  @Test
  void withAsksLimitOrderError() {
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
    assertThat(BookSanityChecker.hasErrors(book))
        .isEqualTo(format("LimitOrder amount is <= 0 for %s", a1));
  }

  @Test
  void withBidsLimitOrderError() {
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
    assertThat(BookSanityChecker.hasErrors(book))
        .isEqualTo(format("LimitOrder amount is <= 0 for %s", b1));
  }

  @Test
  void withBidNoErrorOnNextOrder() {
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
    assertThat(BookSanityChecker.hasErrors(book))
        .isEqualTo(format("LimitOrder amount is <= 0 for %s", b1));
  }

  @Test
  void incorrectBestAskAndBid() {
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
    assertThat(BookSanityChecker.hasErrors(book))
        .isEqualTo(format("Got incorrect best ask and bid %s, %s", a1, b1));
  }

  @Test
  void withBidsWrongPriceOrder() {
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
    assertThat(BookSanityChecker.hasErrors(book))
        .isEqualTo(format("Wrong price order for LimitOrders %s, %s", b2, b1));
  }

  @Test
  void noNextOrder() {
    ArrayList<LimitOrder> limitOrders = new ArrayList<>();
    assertThat(BookSanityChecker.hasErrors(limitOrders.iterator())).isNull();
  }
}
