package org.knowm.xchange.coinone.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinoneTradeResponse {

  private final String result;
  private final String errorCode;
  private final String orderId;
  private final String errorMsg;

  public CoinoneTradeResponse(
      @JsonProperty("result") String result,
      @JsonProperty("errorCode") String errorCode,
      @JsonProperty("orderId") String orderId,
      @JsonProperty("errorMsg") String errorMsg) {

    this.result = result;
    this.errorCode = errorCode;
    this.orderId = orderId;
    this.errorMsg = errorMsg;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public String getResult() {
    return result;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getOrderId() {
    return orderId;
  }
}
