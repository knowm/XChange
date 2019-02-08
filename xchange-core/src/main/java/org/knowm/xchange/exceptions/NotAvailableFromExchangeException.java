package org.knowm.xchange.exceptions;

/**
 * Exception to provide the following to API:
 *
 * <ul>
 *   <li>Indication that the exchange does not support the requested function or data
 * </ul>
 */
public class NotAvailableFromExchangeException extends UnsupportedOperationException {

  /**
   * Constructor
   *
   * @param message Message
   */
  public NotAvailableFromExchangeException(String message) {

    super(message);
  }

  /** Constructor */
  public NotAvailableFromExchangeException() {

    this("Requested Information or function from Exchange is not available.");
  }
}
