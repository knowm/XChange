package org.knowm.xchange.fcoin;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.fcoin.dto.marketdata.FCoinDepth;

public final class FCoinAdapters {

  public static OrderBook adaptOrderBook(FCoinDepth depth, CurrencyPair currencyPair) {
    Stream<LimitOrder> asks =
        adaptLimitOrders(Order.OrderType.ASK, depth.getAsks(), depth.getTs(), currencyPair)
            .sorted();
    Stream<LimitOrder> bids =
        adaptLimitOrders(Order.OrderType.BID, depth.getBids(), depth.getTs(), currencyPair)
            .sorted();
    return new OrderBook(depth.getTs(), asks, bids);
  }

  private static Stream<LimitOrder> adaptLimitOrders(
      Order.OrderType type, BigDecimal[][] list, Date timestamp, CurrencyPair currencyPair) {
    return Arrays.stream(list)
        .map(data -> adaptLimitOrder(type, data, currencyPair, null, timestamp));
  }

  private static LimitOrder adaptLimitOrder(
      Order.OrderType type,
      BigDecimal[] data,
      CurrencyPair currencyPair,
      String id,
      Date timestamp) {

    return new LimitOrder(type, data[1], currencyPair, id, timestamp, data[0]);
  }
}
