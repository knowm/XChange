package org.knowm.xchange.dase;

import org.knowm.xchange.exceptions.ExchangeException;

/**
 * Exception specific to DASE exchange operations.
 *
 * <p>This exception is thrown when the DASE API returns an error that doesn't map to a more
 * specific XChange exception type.
 */
public class DaseException extends ExchangeException {

  public DaseException(String message) {
    super(message);
  }

  public DaseException(String message, Throwable cause) {
    super(message, cause);
  }
}
