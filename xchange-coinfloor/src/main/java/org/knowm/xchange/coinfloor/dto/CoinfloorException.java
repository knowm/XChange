package org.knowm.xchange.coinfloor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.HttpStatusExceptionSupport;

@SuppressWarnings("serial")
public class CoinfloorException extends HttpStatusExceptionSupport {

  private final int errorCode;

  public CoinfloorException(@JsonProperty("error_code") int errorCode, @JsonProperty("error_msg") String reason) {
    super(reason);
    this.errorCode = errorCode;
  }

  public int getErrorCode() {
    return errorCode;
  }
}
