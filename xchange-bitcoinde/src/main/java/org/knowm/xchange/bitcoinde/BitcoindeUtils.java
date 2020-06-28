package org.knowm.xchange.bitcoinde;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;

public class BitcoindeUtils {

  private BitcoindeUtils() {}

  public static String createBitcoindePair(CurrencyPair currencyPair) {
    return createBitcoindeCurrency(currencyPair.base)
        + createBitcoindeCurrency(currencyPair.counter);
  }

  public static String createBitcoindeCurrency(Currency currency) {
    return currency.getCurrencyCode().toLowerCase();
  }

  public static String createBitcoindeType(OrderType type) {
    return type.equals(OrderType.ASK) ? "sell" : "buy";
  }


  public static Integer createBitcoindeBoolean(Boolean value) {
    return Boolean.TRUE.equals(value) ? 1 : 0;
  }
}
