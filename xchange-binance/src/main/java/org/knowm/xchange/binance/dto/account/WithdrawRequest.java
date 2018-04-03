package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class WithdrawRequest extends WapiResponse<String> {
  public final String id;

  public WithdrawRequest(
      @JsonProperty("success") boolean success,
      @JsonProperty("msg") String msg,
      @JsonProperty("id") String id) {
    super(success, msg);
    this.id = id;
  }

  @Override
  public String getData() {
    return id;
  }
}
