package info.bitrich.xchangestream.coinjar;

import info.bitrich.xchangestream.coinjar.dto.CoinjarWebSocketBookEvent;
import info.bitrich.xchangestream.coinjar.dto.CoinjarWebSocketOrderEvent;
import info.bitrich.xchangestream.coinjar.dto.CoinjarWebSocketUserTradeEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.coinjar.CoinjarAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;

class CoinjarStreamingAdapters {

  public static CurrencyPair adaptTopicToCurrencyPair(String topic) {
    if (topic.startsWith("book")) {
      String product = topic.substring(5);
      return CoinjarAdapters.productToCurrencyPair(product);
    } else throw new IllegalArgumentException("Cannot determine topic from topic name " + topic);
  }

  public static String adaptCurrencyPairToBookTopic(CurrencyPair pair) {
    String sep = "";
    if (pair.base.getCurrencyCode().length() > 3 || pair.counter.getCurrencyCode().length() > 3) {
      sep = "-";
    }
    return "book:" + pair.base.toString() + sep + pair.counter.toString();
  }

  public static LimitOrder toLimitOrder(
      CoinjarWebSocketBookEvent.Payload.Order order,
      CurrencyPair currencyPair,
      Order.OrderType orderType) {
    return new LimitOrder.Builder(orderType, currencyPair)
        .originalAmount(new BigDecimal(order.volume))
        .limitPrice(new BigDecimal(order.price))
        .build();
  }

  public static List<LimitOrder> toLimitOrders(
      List<CoinjarWebSocketBookEvent.Payload.Order> orders,
      CurrencyPair currencyPair,
      Order.OrderType orderType) {
    return orders.stream()
        .map(o -> toLimitOrder(o, currencyPair, orderType))
        .collect(Collectors.toList());
  }

  public static UserTrade adaptUserTrade(CoinjarWebSocketUserTradeEvent event) {
    return new UserTrade.Builder()
        .timestamp(Date.from(ZonedDateTime.parse(event.payload.fill.timestamp).toInstant()))
        .id(Long.toString(event.payload.fill.tid))
        .originalAmount(new BigDecimal(event.payload.fill.size))
        .type(CoinjarAdapters.buySellToOrderType(event.payload.fill.side))
        .currencyPair(CoinjarAdapters.productToCurrencyPair(event.payload.fill.productId))
        .price(new BigDecimal(event.payload.fill.price))
        .orderId(Long.toString(event.payload.fill.oid))
        .build();
  }

  public static Order adaptOrder(CoinjarWebSocketOrderEvent event) {
    if ("LMT".equals(event.payload.order.type)) {
      return new LimitOrder.Builder(
              CoinjarAdapters.buySellToOrderType(event.payload.order.side),
              CoinjarAdapters.productToCurrencyPair(event.payload.order.productId))
          .timestamp(Date.from(ZonedDateTime.parse(event.payload.order.timestamp).toInstant()))
          .orderStatus(CoinjarAdapters.adaptStatus(event.payload.order.status))
          .originalAmount(new BigDecimal(event.payload.order.size))
          .limitPrice(new BigDecimal(event.payload.order.price))
          .id(Long.toString(event.payload.order.oid))
          .cumulativeAmount(new BigDecimal(event.payload.order.filled))
          .build();
    } else {
      return new MarketOrder.Builder(
              CoinjarAdapters.buySellToOrderType(event.payload.order.side),
              CoinjarAdapters.productToCurrencyPair(event.payload.order.productId))
          .timestamp(Date.from(ZonedDateTime.parse(event.payload.order.timestamp).toInstant()))
          .orderStatus(CoinjarAdapters.adaptStatus(event.payload.order.status))
          .originalAmount(new BigDecimal(event.payload.order.size))
          .averagePrice(new BigDecimal(event.payload.order.price))
          .id(Long.toString(event.payload.order.oid))
          .cumulativeAmount(new BigDecimal(event.payload.order.filled))
          .build();
    }
  }
}
