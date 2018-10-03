package org.knowm.xchange.binance.dto.account;

public abstract class WapiResponse<T> {

  public final boolean success;
  public final String
      msg; // "{\"code\":-1021,\"msg\":\"Timestamp for this request is not valid.\"}"

  public WapiResponse(boolean success, String msg) {
    super();
    this.success = success;
    this.msg = msg;
  }

  public abstract T getData();
}
