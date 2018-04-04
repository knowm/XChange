package org.knowm.xchange.exceptions;

/** An exception indicating there was an internal server error */
public class InternalServerException extends ExchangeException {

  public InternalServerException(String message) {
    super(message);
  }

  public InternalServerException(Throwable e) {
    super(e);
  }

  public InternalServerException(String message, Throwable cause) {
    super(message, cause);
  }

  public InternalServerException() {
    super("Internal Server Error.");
  }
}
