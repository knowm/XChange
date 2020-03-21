package org.knowm.xchange.bithumb;

import java.util.Optional;
import javax.annotation.Nullable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.instrument.Instrument;

public final class BithumbUtils {

  private BithumbUtils() {
    // not called
  }

  public static String getBaseCurrency(@Nullable Instrument instrument) {
    if (instrument instanceof CurrencyPair) {
      CurrencyPair currencyPair = (CurrencyPair) instrument;
      return Optional.ofNullable(currencyPair)
          .map(c -> c.base)
          .map(Currency::getCurrencyCode)
          .orElse(null);
    }
    return null;
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
