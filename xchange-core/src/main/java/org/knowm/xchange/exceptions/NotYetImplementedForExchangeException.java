package org.knowm.xchange.exceptions;

/**
 * Exception to provide the following to API:
 *
 * <ul>
 *   <li>Indication that the exchange supports the requested function or data, but it's not yet been
 *       implemented
 * </ul>
 */
public class NotYetImplementedForExchangeException extends UnsupportedOperationException {

  /**
   * Constructor
   *
   * @param message Message
   */
  public NotYetImplementedForExchangeException(String message) {

    super(message);
  }

  /** Constructor */
  public NotYetImplementedForExchangeException() {

    this("Feature not yet implemented for exchange.");
  }
}
