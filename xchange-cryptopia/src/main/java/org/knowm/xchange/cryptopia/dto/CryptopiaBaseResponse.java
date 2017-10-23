package org.knowm.xchange.cryptopia.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptopiaBaseResponse<T> {

  private final boolean success;
  private final String message;
  private final T data;
  private final String error;

  @JsonCreator
  public CryptopiaBaseResponse(@JsonProperty("Success") boolean success, @JsonProperty("Message") String message,
      @JsonProperty("Data") T data, @JsonProperty("Error") String error) {
    this.success = success;
    this.message = message;
    this.data = data;
    this.error = error;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }

  public String getError() {
    return error;
  }

  @Override
  public String toString() {
    return "CryptopiaBaseResponse{" +
        "success=" + success +
        ", message='" + message + '\'' +
        ", data=" + data +
        ", error='" + error + '\'' +
        '}';
  }
}
