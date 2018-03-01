package org.knowm.xchange.binance;

import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;

import java.math.BigDecimal;

public class BinanceAdapters {

  private BinanceAdapters() {
  }

  public static String toSymbol(CurrencyPair pair) {
	  if (pair.equals(CurrencyPair.IOTA_BTC)) {
		  return "IOTABTC";
	  }
    return pair.base.getCurrencyCode() + pair.counter.getCurrencyCode();
  }

  public static String toSymbol(Currency currency) {
	  if (Currency.IOT.equals(currency)) {
		  return "IOTA";
	  }
	  return currency.getSymbol();
  }

  public static OrderType convert(OrderSide side) {
    switch (side) {
      case BUY:
        return OrderType.BID;
      case SELL:
        return OrderType.ASK;
      default:
        throw new RuntimeException("Not supported order side: " + side);
    }
  }

  public static OrderSide convert(OrderType type) {
    switch (type) {
      case ASK:
        return OrderSide.SELL;
      case BID:
        return OrderSide.BUY;
      default:
        throw new RuntimeException("Not supported order type: " + type);
    }
  }

  public static long id(String id) {
    try {
      return Long.valueOf(id);
    } catch (Throwable e) {
      throw new RuntimeException("Binance id must be a valid long number.", e);
    }
  }

  public static Order.OrderStatus adaptOrderStatus(OrderStatus orderStatus){
    switch (orderStatus){
      case NEW:
        return Order.OrderStatus.NEW;
      case FILLED:
        return Order.OrderStatus.FILLED;
      case EXPIRED:
        return Order.OrderStatus.EXPIRED;
      case CANCELED:
        return Order.OrderStatus.CANCELED;
      case REJECTED:
        return Order.OrderStatus.REJECTED;
      case PENDING_CANCEL:
        return Order.OrderStatus.PENDING_CANCEL;
      case PARTIALLY_FILLED:
        return Order.OrderStatus.PARTIALLY_FILLED;
      default:
        return Order.OrderStatus.UNKNOWN;
    }
  }

  public static OrderType convertType(boolean isBuyer) {
    return isBuyer ? OrderType.BID : OrderType.ASK;
  }

  public static Order adaptOrder(BinanceOrder order) {
    OrderType type = convert(order.side);
    String currency = order.symbol;
    int pairLength = currency.length();
    CurrencyPair currencyPair;

    Order.OrderStatus orderStatus = adaptOrderStatus(order.status);
    final BigDecimal averagePrice;
    if (order.executedQty.signum() == 0 || order.type.equals(org.knowm.xchange.binance.dto.trade.OrderType.MARKET)) {
      averagePrice = BigDecimal.ZERO;
    } else {
      averagePrice = order.price;
    }

    if (currency.endsWith("USDT")) {
      currencyPair = new CurrencyPair(currency.substring(0, pairLength - 4), "USDT");
    } else {
      currencyPair = new CurrencyPair(currency.substring(0, pairLength - 3), currency.substring(pairLength - 3));
    }

    if (order.type.equals(org.knowm.xchange.binance.dto.trade.OrderType.MARKET)) {
      return new MarketOrder(
              type,
              order.origQty,
              currencyPair,
              Long.toString(order.orderId),
              order.getTime(),
              averagePrice,
              order.executedQty,
              BigDecimal.ZERO,
              orderStatus
      );
    } else if (order.type.equals(org.knowm.xchange.binance.dto.trade.OrderType.LIMIT)) {
      if (order.stopPrice.signum() == 0) {
        return new LimitOrder(
                type,
                order.origQty,
                currencyPair,
                Long.toString(order.orderId),
                order.getTime(),
                order.price,
                averagePrice,
                order.executedQty,
                BigDecimal.ZERO,
                orderStatus);
      } else {
        return new StopOrder(
                type,
                order.origQty,
                currencyPair,
                Long.toString(order.orderId),
                order.getTime(),
                order.stopPrice,
                averagePrice,
                order.executedQty,
                orderStatus);
      }
    }
    return null;
  }
}
