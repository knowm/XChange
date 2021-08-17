package org.knowm.xchange.binance.dto.account;

public abstract class ApiResponse<T> {

  public final boolean success;
  public final String
      msg; // "{\"code\":-1021,\"msg\":\"Timestamp for this request is not valid.\"}"

  public ApiResponse(boolean success, String msg) {
    this.success = success;
    this.msg = msg;
  }

  public abstract T getData();
}
