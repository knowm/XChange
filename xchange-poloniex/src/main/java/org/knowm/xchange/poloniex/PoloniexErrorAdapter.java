package org.knowm.xchange.poloniex;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.poloniex.dto.PoloniexException;

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
    if (StringUtils.isEmpty(message)) {
      return new ExchangeException("Operation failed without any error message");
    }
    if (message.startsWith(INVALID_CURRENCY_MESSAGE_START)) {
      return new CurrencyPairNotValidException(message);
    }
    return new ExchangeException(message, e);
  }
}
