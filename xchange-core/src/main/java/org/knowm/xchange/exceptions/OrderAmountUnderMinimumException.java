package org.knowm.xchange.exceptions;

/**
 * Exception indicating that the amount in the order you tried to place of verify was under the
 * minimum accepted by the exchange
 */
public class OrderAmountUnderMinimumException extends OrderNotValidException {

  public OrderAmountUnderMinimumException() {
    super("Orders amount under minimum");
  }

  public OrderAmountUnderMinimumException(String message) {
    super(message);
  }

  public OrderAmountUnderMinimumException(String message, Throwable cause) {
    super(message, cause);
  }
}
