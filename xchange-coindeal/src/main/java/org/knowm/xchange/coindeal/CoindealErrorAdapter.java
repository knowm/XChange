package org.knowm.xchange.coindeal;

import org.knowm.xchange.coindeal.dto.CoindealException;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;

public final class CoindealErrorAdapter {

  private static final String INVALID_CURRENCY_PAIR_MESSAGE = "invalid currency pair";

  private CoindealErrorAdapter() {}

  public static ExchangeException adapt(CoindealException e) {
    if (e.getHttpStatusCode() == 422 && e.getErrors() != null) {
      String errorsStr = e.getErrors().toString().toLowerCase();
      if (errorsStr.contains(INVALID_CURRENCY_PAIR_MESSAGE)) {
        throw new CurrencyPairNotValidException();
      }
    }
    return new ExchangeException(e.getMessage());
  }
}
