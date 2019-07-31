package org.knowm.xchange.lgo;

import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.FrequencyLimitExceededException;
import org.knowm.xchange.exceptions.InternalServerException;
import si.mazi.rescu.HttpStatusIOException;

public class LgoErrorAdapter {

  private LgoErrorAdapter() {}

  /** Parse errors from HTTP exceptions */
  public static void adapt(HttpStatusIOException httpStatusException) {
    int statusCode = httpStatusException.getHttpStatusCode();
    if (statusCode == 401) {
      throw new ExchangeSecurityException(httpStatusException);
    }
    if (statusCode == 429) {
      throw new FrequencyLimitExceededException(httpStatusException.getMessage());
    }
    if (statusCode == 500) {
      throw new InternalServerException(httpStatusException);
    }
    throw new ExchangeException(httpStatusException.getMessage(), httpStatusException);
  }
}
