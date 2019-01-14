package org.knowm.xchange.bithumb;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

public final class BithumbUtils {

  private BithumbUtils() {
    // not called
  }

  public static String getBaseCurrency(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode();
  }

  public static String getCounterCurrency() {
    return Currency.KRW.getCurrencyCode();
  }

  public static String fromOrderType(Order.OrderType type) {
    switch (type) {
      case BID:
        return "bid";
      case ASK:
        return "ask";
      default:
        throw new NotAvailableFromExchangeException();
    }
  }
}
