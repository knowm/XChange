package org.knowm.xchange.exceptions;

/** An exception indicating there the rate limit for making requests has been exceeded */
public class SystemOverloadException extends ExchangeException {

  public SystemOverloadException(String message) {
    super(message);
  }

  public SystemOverloadException(Throwable e) {
    super(e);
  }

  public SystemOverloadException(String message, Throwable cause) {
    super(message, cause);
  }

  public SystemOverloadException() {
    super("The system is currently overloaded.");
  }
}
