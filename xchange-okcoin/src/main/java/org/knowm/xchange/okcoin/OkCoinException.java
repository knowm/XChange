package org.knowm.xchange.okcoin;

import org.knowm.xchange.exceptions.ExchangeException;

public class OkCoinException extends ExchangeException {

  private static final long serialVersionUID = 20140614L;

  private final int errorCode;

  /**
   * Constructor.
   *
   * @param errorCode the error code.
   * @param message the exception message.
   */
  public OkCoinException(int errorCode, String message) {

    super(message);
    this.errorCode = errorCode;
  }

  /** @return the error code. */
  public int getErrorCode() {

    return errorCode;
  }
}
