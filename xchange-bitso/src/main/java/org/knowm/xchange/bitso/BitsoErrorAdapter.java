package org.knowm.xchange.bitso;

import java.net.HttpURLConnection;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.RateLimitExceededException;

public class BitsoErrorAdapter {

  /**
   * Adapts BitsoException to appropriate XChange exception
   *
   * @param e The BitsoException to adapt
   * @return An appropriate XChange exception
   */
  public static ExchangeException adapt(BitsoException e) {
    int statusCode = e.getHttpStatusCode();
    String message = e.getMessage();

    // Check HTTP status codes
    if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
      return new ExchangeException("Authentication failed: " + message, e);
    } else if (statusCode == 429) { // Too Many Requests
      return new RateLimitExceededException(message, e);
    }

    // Check for specific error messages in the response
    if (message != null) {
      String lowerMessage = message.toLowerCase();
      if (lowerMessage.contains("insufficient funds")
          || lowerMessage.contains("insufficient balance")
          || lowerMessage.contains("not enough balance")) {
        return new FundsExceededException(message, e);
      } else if (lowerMessage.contains("rate limit")
          || lowerMessage.contains("too many requests")) {
        return new RateLimitExceededException(message, e);
      }
    }

    // Default to generic ExchangeException
    return new ExchangeException(message, e);
  }
}
