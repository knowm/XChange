package org.knowm.xchange.bitcointoyou;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class BitcointoyouException extends RuntimeException {

  @JsonProperty("error")
  final String error;

  public BitcointoyouException(@JsonProperty("error") String error) {

    super();
    this.error = error;
  }

  public String getError() {

    return error;
  }
}
