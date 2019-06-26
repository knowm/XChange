package org.knowm.xchange.deribit.v2;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import org.knowm.xchange.deribit.v2.dto.DeribitError;
import org.knowm.xchange.deribit.v2.dto.DeribitException;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;

public class DeribitErrorAdapter {

  public DeribitErrorAdapter() {}

  /** Parse errors from HTTP exceptions */
  public static ExchangeException adapt(DeribitException ex) {

    DeribitError error = ex.getError();

    if (error != null
        && error.getClass().equals(DeribitError.class)
        && isNotEmpty(error.getMessage())) {

      int code = error.getCode();
      String msg = error.getMessage();
      String data = error.getData().toString();
      if (isNotEmpty(data)) {
        msg += " - " + data;
      }

      switch (code) {
        case -32602:
          return new CurrencyPairNotValidException(data, ex);
        default:
          return new ExchangeException(msg, ex);
      }
    }

    return new ExchangeException("Operation failed without any error message", ex);
  }
}
