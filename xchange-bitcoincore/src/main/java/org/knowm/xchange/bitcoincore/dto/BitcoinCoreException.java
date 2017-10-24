package org.knowm.xchange.bitcoincore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class BitcoinCoreException extends RuntimeException {

  private final String error;

  public BitcoinCoreException(@JsonProperty("error") String error) {
    this.error = error;
  }

  @Override
  public String getMessage() {
    return error;
  }
}
