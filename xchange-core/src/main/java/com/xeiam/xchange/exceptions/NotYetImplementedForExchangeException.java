package com.xeiam.xchange.exceptions;

/**
 * <p>
 * Exception to provide the following to API:
 * </p>
 * <ul>
 * <li>Indication that the exchange supports the requested function or data, but it's not yet been implemented</li>
 * </ul>
 */
public class NotYetImplementedForExchangeException extends RuntimeException {

  /**
   * Constructor
   *
   * @param message
   */
  private NotYetImplementedForExchangeException(String message) {

    super(message);
  }

  /**
   * Constructor
   */
  public NotYetImplementedForExchangeException() {

    this("Feature not yet implemented for exchange.");
  }

}
