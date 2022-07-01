package org.knowm.xchange.latoken;

import java.net.HttpURLConnection;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.latoken.dto.LatokenException;

/** @author Ezer */
public final class LatokenErrorAdapter {

  private LatokenErrorAdapter() {}

  public static ExchangeException adapt(LatokenException e) {
    String message = e.getMessage();
    if (StringUtils.isEmpty(message)) {
      message = "Operation failed without any error message";
    }
    switch (e.getHttpStatusCode()) {
      case HttpURLConnection.HTTP_BAD_REQUEST: // 400
        return new ExchangeException(message, e);

      case HttpURLConnection.HTTP_UNAUTHORIZED: // 401
        return new ExchangeSecurityException(message, e);

      case 428:
        return new RateLimitExceededException(message, e);
    }
    return new ExchangeException(message, e);
  }
}
