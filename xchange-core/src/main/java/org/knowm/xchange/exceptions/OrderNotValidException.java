package org.knowm.xchange.exceptions;

/** Exception indicating place of verify an order which was not valid */
public class OrderNotValidException extends ExchangeException {

  public OrderNotValidException() {
    super("Invalid order");
  }

  public OrderNotValidException(String message) {
    super(message);
  }

  public OrderNotValidException(String message, Throwable cause) {
    super(message, cause);
  }
}
