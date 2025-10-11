package org.knowm.xchange.dase;

import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.RateLimitExceededException;

/**
 * Adapter to convert DASE API error types into appropriate XChange exception types.
 *
 * <p>Maps error types from the DASE API specification to the most appropriate XChange exception
 * class.
 */
public final class DaseErrorAdapter {

  private DaseErrorAdapter() {}

  /**
   * Converts a DASE API error type and message into an appropriate XChange exception.
   *
   * @param type the error type from the API (e.g., "InsufficientFunds", "Unauthorized")
   * @param message the error message from the API
   * @return an appropriate exception instance
   */
  public static ExchangeException adapt(String type, String message) {
    if (type == null) {
      return new DaseException(message != null ? message : "Unknown error");
    }

    final String effectiveMessage = message != null ? message : type;

    switch (type) {
      // Security / Authentication errors (401, 403)
      case "Unauthorized":
      case "Forbidden":
        return new ExchangeSecurityException(effectiveMessage);

      // Funds errors (400)
      case "InsufficientFunds":
        return new FundsExceededException(effectiveMessage);

      // Rate limiting (429)
      case "TooManyRequests":
        return new RateLimitExceededException(effectiveMessage);

      // Input validation errors (400)
      case "InvalidInput":
      case "InvalidIdFormat":
      case "InvalidNumberFormat":
      case "PayloadTooLarge":
      case "MarketOrdersDisabled":
        return new DaseException(effectiveMessage);

      // Resource errors (404)
      case "NotFound":
        return new DaseException(effectiveMessage);

      // Service availability errors (503)
      case "ServiceStarting":
      case "ServiceRestarting":
      case "ServiceUnavailable":
      case "ServiceReadOnly":
      case "ServiceCancelOnly":
      case "ServicePostOnly":
      case "ServiceShuttingDown":
        return new DaseException(effectiveMessage);

      // Internal errors (500)
      case "InternalError":
        return new DaseException(effectiveMessage);

      // Unknown error type
      default:
        return new DaseException(effectiveMessage);
    }
  }
}
