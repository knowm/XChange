package org.knowm.xchange.exceptions;

/**
 * Indicates that the cause the error ware wrong credentials or insufficient privileges.
 *
 * <p>We throw this exception only for exchanges where we can’t clearly distinguish this cause from
 * other error types. If an API does not provide proper error information or the modules
 * implementation is lacking then an ExchangeException will be thrown in this situation.
 *
 * @author walec51
 */
public class ExchangeSecurityException extends ExchangeException {

  private static final String DEFAULT_MESSAGE = "Wrong credentials or insufficient privileges";

  public ExchangeSecurityException() {
    super(DEFAULT_MESSAGE);
  }

  public ExchangeSecurityException(String message) {
    super(message);
  }

  public ExchangeSecurityException(Throwable cause) {
    super(DEFAULT_MESSAGE, cause);
  }

  public ExchangeSecurityException(String message, Throwable cause) {
    super(message, cause);
  }
}
