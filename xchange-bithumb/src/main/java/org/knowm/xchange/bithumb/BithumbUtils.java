package org.knowm.xchange.bithumb;

import java.util.Optional;
import javax.annotation.Nullable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

public final class BithumbUtils {

  private BithumbUtils() {
    // not called
  }

  public static String getBaseCurrency(@Nullable CurrencyPair currencyPair) {
    return Optional.ofNullable(currencyPair)
        .map(c -> c.getBase())
        .map(Currency::getCurrencyCode)
        .orElse(null);
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
