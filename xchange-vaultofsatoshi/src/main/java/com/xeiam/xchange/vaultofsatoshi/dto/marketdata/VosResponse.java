package com.xeiam.xchange.vaultofsatoshi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response wrapper for VaultOfSatoshi which contains status, data and error message (if applicable)
 */

public final class VosResponse<T> {

  private final String status;
  private final String message;
  private final T data;

  @JsonCreator
  public VosResponse(@JsonProperty("data") T data, @JsonProperty("status") String status, @JsonProperty("message") String message) {

    this.status = status;
    this.data = data;
    this.message = message;
  }

  public String getStatus() {

    return status;
  }

  public String getMessage() {

    return message;
  }

  public T getData() {

    return data;
  }

  @Override
  public String toString() {

    return "VosWrapper [status=" + status + ", message=" + message + ", data=" + data + "]";
  }

}
