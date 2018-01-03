package org.knowm.xchange.binance;

import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;

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

  public static OrderType convertType(boolean isBuyer) {
    return isBuyer ? OrderType.BID : OrderType.ASK;
  }
}
