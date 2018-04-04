package org.knowm.xchange.okcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinErrorResult {

  private final boolean result;
  private final int errorCode;

  public OkCoinErrorResult(
      @JsonProperty("result") final boolean result,
      @JsonProperty("error_code") final int errorCode) {

    this.result = result;
    this.errorCode = errorCode;
  }

  public boolean isResult() {

    return result;
  }

  public int getErrorCode() {

    return errorCode;
  }
}
