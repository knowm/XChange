package org.knowm.xchange.lgo;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.FrequencyLimitExceededException;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.lgo.dto.LgoException;

public class LgoErrorAdapter {

  private LgoErrorAdapter() {}

  public static ExchangeException adapt(LgoException exception) {
    String message = exception.getMessage();
    if (StringUtils.isEmpty(message)) {
      message = "Operation failed without any error message";
    }
    switch (exception.getHttpStatusCode()) {
      case 401:
        return new ExchangeSecurityException(message, exception);
      case 429:
        return new FrequencyLimitExceededException(message);
      case 500:
        return new InternalServerException(message, exception);
    }
    return new ExchangeException(message, exception);
  }
}
