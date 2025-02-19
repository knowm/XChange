package org.knowm.xchange.bl3p;

import java.math.BigDecimal;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

public class Bl3pUtils {
  private Bl3pUtils() {}

  private static final BigDecimal SATOSHI = new BigDecimal(1e8);
  private static final BigDecimal EUROSHI = new BigDecimal(1e5);

  public static final BigDecimal fromSatoshi(BigDecimal bd) {
    return bd.divide(SATOSHI);
  }

  public static final long toSatoshi(BigDecimal bd) {
    return bd.multiply(SATOSHI).longValue();
  }

  public static final BigDecimal fromEuroshi(BigDecimal bd) {
    return bd.divide(EUROSHI);
  }

  public static final long toEuroshi(BigDecimal bd) {
    return bd.multiply(EUROSHI).longValue();
  }

  public static String toPairString(CurrencyPair currencyPair) {
    return currencyPair.getBase().getCurrencyCode() + currencyPair.getCounter().getCurrencyCode();
  }

  public static Order.OrderType fromBl3pOrderType(String bl3pType) {
    return bl3pType.equals("bid") ? Order.OrderType.BID : Order.OrderType.ASK;
  }

  public static String toBl3pOrderType(Order.OrderType orderType) {
    switch (orderType) {
      case BID:
        return "bid";
      case ASK:
        return "ask";
      case EXIT_ASK:
        return "exit_ask";
      case EXIT_BID:
        return "exit_bid";
      default:
        return null;
    }
  }
}
