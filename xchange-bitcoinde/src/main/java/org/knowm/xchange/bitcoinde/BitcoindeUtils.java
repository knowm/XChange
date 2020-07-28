package org.knowm.xchange.bitcoinde;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;

public class BitcoindeUtils {

  private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

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

  public static String rfc3339Timestamp(Date date) {
    return DATE_FORMATTER.format(date);
  }

  public static Integer createBitcoindeBoolean(Boolean value) {
    return Boolean.TRUE.equals(value) ? 1 : 0;
  }
}
