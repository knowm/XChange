package org.knowm.xchange.coinjar;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.coinjar.dto.CoinjarOrder;
import org.knowm.xchange.coinjar.dto.data.CoinjarOrderBook;
import org.knowm.xchange.coinjar.dto.data.CoinjarTicker;
import org.knowm.xchange.coinjar.dto.trading.CoinjarFill;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinjarAdapters {

  private static final Logger logger = LoggerFactory.getLogger(CoinjarAdapters.class);

  private CoinjarAdapters() {}

  public static String currencyPairToProduct(CurrencyPair pair) {
    return pair.base.getCurrencyCode() + pair.counter.getCurrencyCode();
  }

  public static CurrencyPair productToCurrencyPair(String product) {
    return new CurrencyPair(
        Currency.getInstance(product.substring(0, 3)),
        Currency.getInstance(product.substring(3, 6)));
  }

  public static String orderTypeToBuySell(Order.OrderType orderType) {
    if (orderType == Order.OrderType.BID) {
      return "buy";
    } else if (orderType == Order.OrderType.ASK) {
      return "sell";
    } else
      throw new IllegalArgumentException(
          "Unable to convert orderType " + orderType + " to buy/sell");
  }

  public static Order.OrderType buySellToOrderType(String buySell) {
    if (buySell.equals("buy")) {
      return Order.OrderType.BID;
    } else if (buySell.equals("sell")) {
      return Order.OrderType.ASK;
    } else
      throw new IllegalArgumentException(
          "Unable to convert orderType " + buySell + " to Order.OrderType");
  }

  public static Order.OrderStatus adaptStatus(String status) {
    if (status.equals("booked")) {
      return Order.OrderStatus.PENDING_NEW;
    } else if (status.equals("filled")) {
      return Order.OrderStatus.FILLED;
    } else if (status.equals("cancelled")) {
      return Order.OrderStatus.CANCELED;
    } else {
      logger.warn("Unable to convert remote status {} to Order.OrderStatus", status);
      return Order.OrderStatus.UNKNOWN;
    }
  }

  public static Ticker adaptTicker(CoinjarTicker ticker, CurrencyPair currencyPair)
      throws CoinjarException {
    try {
      return new Ticker.Builder()
          .currencyPair(currencyPair)
          .last(new BigDecimal(ticker.last))
          .bid(new BigDecimal(ticker.bid))
          .ask(new BigDecimal(ticker.ask))
          .timestamp(DateUtils.fromISODateString(ticker.currentTime))
          .volume(new BigDecimal(ticker.volume))
          .build();
    } catch (InvalidFormatException e) {
      throw new CoinjarException(
          "adaptTicker cannot parse date " + ticker.currentTime, Collections.emptyList());
    }
  }

  public static LimitOrder adaptOrderToLimitOrder(CoinjarOrder coinjarOrder) {
    BigDecimal originalAmount = new BigDecimal(coinjarOrder.size);
    BigDecimal filled = new BigDecimal(coinjarOrder.filled);
    Boolean isFilled = filled.compareTo(BigDecimal.ZERO) > 0;
    BigDecimal remainingAmount = originalAmount.subtract(filled);
    Order.OrderStatus orderStatus = adaptStatus(coinjarOrder.status);
    if (orderStatus == Order.OrderStatus.PENDING_NEW && isFilled) {
      orderStatus = Order.OrderStatus.PARTIALLY_FILLED;
    } else if (orderStatus == Order.OrderStatus.CANCELED && isFilled) {
      orderStatus = Order.OrderStatus.PARTIALLY_CANCELED;
    }
    return new LimitOrder.Builder(
            buySellToOrderType(coinjarOrder.orderSide),
            productToCurrencyPair(coinjarOrder.productId))
        .id(coinjarOrder.oid.toString())
        .limitPrice(new BigDecimal(coinjarOrder.price))
        .originalAmount(originalAmount)
        .remainingAmount(remainingAmount)
        .cumulativeAmount(filled)
        .averagePrice(new BigDecimal(coinjarOrder.price))
        .timestamp(
            Date.from(
                ZonedDateTime.parse(coinjarOrder.timestamp, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                    .toInstant()))
        .orderStatus(orderStatus)
        .build();
  }

  public static UserTrade adaptOrderToUserTrade(CoinjarOrder order) {
    return new UserTrade.Builder()
        .id(order.oid.toString())
        .orderId(order.oid.toString())
        .currencyPair(productToCurrencyPair(order.productId))
        .type(buySellToOrderType(order.orderSide))
        .price(new BigDecimal(order.price))
        .originalAmount(new BigDecimal(order.size))
        .timestamp(Date.from(ZonedDateTime.parse(order.timestamp).toInstant()))
        .build();
  }

  private static List<LimitOrder> adaptOrderList(
      List<List<String>> orderList, Order.OrderType orderType, CurrencyPair pair) {
    return orderList.stream()
        .map(
            l ->
                new LimitOrder(
                    orderType,
                    new BigDecimal(l.get(1)),
                    pair,
                    null,
                    null,
                    new BigDecimal(l.get(0))))
        .collect(Collectors.toList());
  }

  public static OrderBook adaptOrderbook(CoinjarOrderBook orderBook, CurrencyPair currencyPair) {

    return new OrderBook(
        null,
        adaptOrderList(orderBook.asks, Order.OrderType.ASK, currencyPair),
        adaptOrderList(orderBook.bids, Order.OrderType.BID, currencyPair));
  }

  public static UserTrade adaptFillToUserTrade(CoinjarFill coinjarFill) {
    return new UserTrade.Builder()
        .id(coinjarFill.tid.toString())
        .orderId(coinjarFill.oid.toString())
        .currencyPair(productToCurrencyPair(coinjarFill.productId))
        .type(buySellToOrderType(coinjarFill.side))
        .price(new BigDecimal(coinjarFill.price))
        .originalAmount(new BigDecimal(coinjarFill.size))
        .timestamp(Date.from(ZonedDateTime.parse(coinjarFill.timestamp).toInstant()))
        .build();
  }
}
