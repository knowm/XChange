package org.knowm.xchange.exceptions;

/** An exception indicating that the server is not available, possibly due to downtime */
public class ExchangeUnavailableException extends ExchangeException {

  private static final long serialVersionUID = -194770176188883080L;

  public ExchangeUnavailableException(String message) {
    super(message);
  }

  public ExchangeUnavailableException(Throwable e) {
    super(e);
  }

  public ExchangeUnavailableException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExchangeUnavailableException() {
    super("Service unavailable");
  }
}
