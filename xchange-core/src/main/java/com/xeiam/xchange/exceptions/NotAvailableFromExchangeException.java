package com.xeiam.xchange.exceptions;

/**
 * <p>
 * Exception to provide the following to API:
 * </p>
 * <ul>
 * <li>Indication that the exchange does not support the requested function or data</li>
 * </ul>
 */
public class NotAvailableFromExchangeException extends RuntimeException {

  private static final long serialVersionUID = 2015041101L;

  /**
   * Constructor
   * 
   * @param message
   */
  private NotAvailableFromExchangeException(String message) {

    super(message);
  }

  /**
   * Constructor
   */
  public NotAvailableFromExchangeException() {

    this("Requested Information from Exchange is not available.");
  }

}
