package org.knowm.xchange.cryptocom;

import org.knowm.xchange.cryptocom.dto.CryptoComResponse;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.exceptions.RateLimitExceededException;

/**
 * Maps Crypto.com Exchange v1 numeric error codes (see "Common API Reference" in the API docs) to
 * XChange exceptions.
 */
public class CryptoComErrorAdapter {

  public static final int NO_POSITION = 213;
  public static final int INVALID_NONCE = 10004;
  public static final int TOO_MANY_REQUESTS = 429;
  public static final int DUPLICATE_CLIENT_OID = 315;
  public static final int INSUFFICIENT_AVAILABLE_BALANCE = 20007;
  public static final int EXCEED_MAX_TRADABLE_AMOUNT = 316;

  public static ExchangeException adaptError(CryptoComResponse response) {
    switch (response.getCode()) {
      case INSUFFICIENT_AVAILABLE_BALANCE:
      case EXCEED_MAX_TRADABLE_AMOUNT:
        return new FundsExceededException(response.getMessage());
      case TOO_MANY_REQUESTS:
        return new RateLimitExceededException(response.getMessage());
      case INVALID_NONCE:
        return new NonceException(response.getMessage());
      default:
        return new ExchangeException(
            String.format(
                "Crypto.com code: %d error: %s", response.getCode(), response.getMessage()));
    }
  }
}
