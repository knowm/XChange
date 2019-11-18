package org.knowm.xchange.bitfinex.dto;

public class BitfinexException extends RuntimeException {

  public BitfinexException() {}

  public BitfinexException(String message) {
    super(message);
  }
}
