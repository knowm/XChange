package org.knowm.xchange.poloniex;

import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;

/** @author walec51 */
public class PoloniexErrorAdapter {

  private static final String INVALID_CURRENCY_MESSAGE_START = "Invalid currency pair";

  public static ExchangeException adapt(PoloniexException e) {
    switch (e.getHttpStatusCode()) {
      case 403:
        return new ExchangeSecurityException(e);
      default:
        return adaptBasedOnErrorMessage(e);
    }
  }

  private static ExchangeException adaptBasedOnErrorMessage(PoloniexException e) {
    String message = e.getError();
    if (message.startsWith(INVALID_CURRENCY_MESSAGE_START)) {
      return new CurrencyPairNotValidException(message);
    }
    return new ExchangeException(message, e);
  }
}
