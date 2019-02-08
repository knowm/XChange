package org.knowm.xchange.bity;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bity.dto.BityException;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;

public class BityErrorAdapter {

  public static ExchangeException adapt(BityException e) {
    String message = e.getMessage();
    if (StringUtils.isEmpty(message)) {
      return new ExchangeException("Operation failed without any error message");
    }
    switch (message) {
      case "INVALID_MARKET":
        return new CurrencyPairNotValidException();
    }
    return new ExchangeException(message);
  }
}
