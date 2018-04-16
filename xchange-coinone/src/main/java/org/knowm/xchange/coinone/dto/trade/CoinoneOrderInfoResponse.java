package org.knowm.xchange.coinone.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinoneOrderInfoResponse {

  private final String result;
  private final String errorCode;
  private final String status;
  private final CoinoneOrderInfo info;

  public CoinoneOrderInfoResponse(
      @JsonProperty("result") String result,
      @JsonProperty("errorCode") String errorCode,
      @JsonProperty("status") String status,
      @JsonProperty("info") CoinoneOrderInfo info) {
    this.result = result;
    this.errorCode = errorCode;
    this.status = status;
    this.info = info;
  }

  public String getResult() {
    return result;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getStatus() {
    return status;
  }

  public CoinoneOrderInfo getInfo() {
    return info;
  }
}
