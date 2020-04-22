package org.knowm.xchange.cryptowatch.dto;

import org.knowm.xchange.exceptions.ExchangeException;

public class CryptowatchException extends ExchangeException {

  public CryptowatchException(String message) {
    super(message);
  }

  public CryptowatchException(String message, Throwable cause) {
    super(message, cause);
  }

  public CryptowatchException(Throwable cause) {
    super(cause);
  }
}
