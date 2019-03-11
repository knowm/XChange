package org.knowm.xchange.coinone.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinoneWithdrawResponse {

  private final String result;
  private final String errorCode;
  private final String txid;

  public CoinoneWithdrawResponse(
      @JsonProperty("result") String result,
      @JsonProperty("errorCode") String errorCode,
      @JsonProperty("txid") String txid) {
    this.result = result;
    this.errorCode = errorCode;
    this.txid = txid;
  }

  public String getResult() {
    return result;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getTxid() {
    return txid;
  }
}
