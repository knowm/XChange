package org.knowm.xchange.upbit;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public class UpbitUtils {

  public static final String MARKET_NAME_SEPARATOR = "-";

  public static String toPairString(CurrencyPair currencyPair) {
    if (currencyPair == null) return null;
    return currencyPair.counter.getCurrencyCode().toUpperCase()
        + MARKET_NAME_SEPARATOR
        + currencyPair.base.getCurrencyCode().toUpperCase();
  }

  public static CurrencyPair toCurrencyPair(String pairString) {
    if (pairString == null) return null;
    String[] pairStringSplit = pairString.split(MARKET_NAME_SEPARATOR);
    if (pairStringSplit.length != 2) return null;
    return new CurrencyPair(pairStringSplit[1], pairStringSplit[0]);
  }

  public static String toSide(Order.OrderType type) {
    if (type == null) {
      return null;
    }

    switch (type) {
      case ASK:
        return "ask";
      case BID:
        return "bid";
      default:
        throw new NotYetImplementedForExchangeException();
    }
  }

  public static Order.OrderType fromSide(String side) {
    if (side == null) {
      return null;
    }

    switch (side) {
      case "ask":
        return Order.OrderType.ASK;
      case "bid":
        return Order.OrderType.BID;
      default:
        return null;
    }
  }
}
