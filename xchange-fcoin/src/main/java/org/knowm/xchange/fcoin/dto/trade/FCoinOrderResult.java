package org.knowm.xchange.fcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FCoinOrderResult {

  private final int status;
  private final String data;
  private final String msg;

  public FCoinOrderResult(
      @JsonProperty("status") int status,
      @JsonProperty("data") String data,
      @JsonProperty("msg") String msg) {
    this.status = status;
    this.data = data;
    this.msg = msg;
  }

  public int getStatus() {
    return status;
  }

  public String getData() {
    return data;
  }

  public String getMsg() {
    return msg;
  }
}
