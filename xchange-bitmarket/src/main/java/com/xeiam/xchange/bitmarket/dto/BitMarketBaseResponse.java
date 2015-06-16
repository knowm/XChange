package com.xeiam.xchange.bitmarket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class BitMarketBaseResponse<T> {


  private final Boolean success;
  private final T data;
  private final String error;
  private final String errorMsg;
  private final Long time;
  private final BitMarketRequestLimit limit;

  public BitMarketBaseResponse(@JsonProperty("success") Boolean success,
                               @JsonProperty("data") T data,
                               @JsonProperty("error") String error,
                               @JsonProperty("errorMsg") String errorMsg,
                               @JsonProperty("time") Long time,
                               @JsonProperty("limit") BitMarketRequestLimit limit) {
    this.success = success;
    this.data = data;
    this.error = error;
    this.errorMsg = errorMsg;
    this.time = time;
    this.limit = limit;
  }

  public Boolean isSuccess() {
    return success;
  }

  public T getData() {
    return data;
  }

  public String getError() {
    return error;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public Long getTime() {
    return time;
  }

  public BitMarketRequestLimit getLimit() {
    return limit;
  }
}
