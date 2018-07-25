package org.knowm.xchange.coindirect;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectOrderbook;
import org.knowm.xchange.coindirect.dto.trade.CoindirectOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;

public class CoindirectAdapters {
  public static String toSymbol(CurrencyPair pair) {
    return pair.base.getCurrencyCode() + "-" + pair.counter.getCurrencyCode();
  }

  public static CurrencyPair toCurrencyPair(String symbol) {
    int token = symbol.indexOf('-');
    String left = symbol.substring(0, token);
    String right = symbol.substring(token + 1);
    return new CurrencyPair(left, right);
  }

  public static Currency toCurrency(String code) {
    return Currency.getInstance(code);
  }

  public static Order.OrderType convert(CoindirectOrder.Side side) {
    switch (side) {
      case BUY:
        return Order.OrderType.BID;
      case SELL:
        return Order.OrderType.ASK;
      default:
        throw new RuntimeException("Not supported order side: " + side);
    }
  }

  public static CoindirectOrder.Side convert(Order.OrderType type) {
    switch (type) {
      case BID:
        return CoindirectOrder.Side.BUY;
      case ASK:
        return CoindirectOrder.Side.SELL;
      default:
        throw new RuntimeException("Not supported order side: " + type);
    }
  }

  public static Order.OrderStatus adaptOrderStatus(CoindirectOrder.Status orderStatus) {
    switch (orderStatus) {
      case PLACED:
      case SUBMITTED:
        return Order.OrderStatus.NEW;
      case COMPLETED:
        return Order.OrderStatus.FILLED;
      case PARTIAL_CANCEL:
      case CANCELLED:
        return Order.OrderStatus.CANCELED;
      case PENDING_CANCEL:
        return Order.OrderStatus.PENDING_CANCEL;
      case PARTIAL:
        return Order.OrderStatus.PARTIALLY_FILLED;
      case ERROR:
        return Order.OrderStatus.REJECTED;
      default:
        return Order.OrderStatus.UNKNOWN;
    }
  }

  public static OrderBook adaptOrderBook(
      CurrencyPair currencyPair, CoindirectOrderbook coindirectOrderbook) {
    List<LimitOrder> bids =
        coindirectOrderbook
            .bids
            .stream()
            .map(
                e -> new LimitOrder(Order.OrderType.BID, e.size, currencyPair, null, null, e.price))
            .collect(Collectors.toList());
    List<LimitOrder> asks =
        coindirectOrderbook
            .asks
            .stream()
            .map(
                e -> new LimitOrder(Order.OrderType.ASK, e.size, currencyPair, null, null, e.price))
            .collect(Collectors.toList());

    return new OrderBook(null, asks, bids);
  }

  public static Order adaptOrder(CoindirectOrder order) {
    Order.OrderType type = convert(order.side);
    CurrencyPair currencyPair = toCurrencyPair(order.symbol);

    Order.OrderStatus orderStatus = adaptOrderStatus(order.status);
    final BigDecimal averagePrice;
    if (order.executedAmount.signum() == 0) {
      averagePrice = BigDecimal.ZERO;
    } else {
      averagePrice = order.executedPrice;
    }

    Order result = null;
    if (order.type.equals(CoindirectOrder.Type.MARKET)) {
      result =
          new MarketOrder(
              type,
              order.amount,
              currencyPair,
              order.uuid,
              order.dateCreated,
              averagePrice,
              order.executedAmount,
              order.executedFees,
              orderStatus);
    } else if (order.type.equals(CoindirectOrder.Type.LIMIT)) {
      result =
          new LimitOrder(
              type,
              order.amount,
              currencyPair,
              order.uuid,
              order.dateCreated,
              order.price,
              averagePrice,
              order.executedAmount,
              order.executedFees,
              orderStatus);
    }

    return result;
  }
}
