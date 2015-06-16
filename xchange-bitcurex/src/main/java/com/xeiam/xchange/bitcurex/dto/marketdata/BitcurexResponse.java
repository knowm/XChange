package com.xeiam.xchange.bitcurex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Yaroslav
 * Date: 27/03/15
 * Time: 17:24
 */
public class BitcurexResponse<T> {

  private String status;
  private String error;
  private T data;

  public BitcurexResponse(@JsonProperty("status") String status,
                          @JsonProperty("error") String error,
                          @JsonProperty("data") T data) {
    this.status = status;
    this.error = error;
    this.data = data;
  }

  public String getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public T getData() {
    return data;
  }

  @Override
  public String toString() {
    return "BitcurexResponse{" +
        "status='" + status + '\'' +
        ", error='" + error + '\'' +
        ", data=" + data +
        '}';
  }
}
