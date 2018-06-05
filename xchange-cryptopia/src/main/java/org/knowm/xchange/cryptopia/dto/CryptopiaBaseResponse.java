package org.knowm.xchange.cryptopia.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import si.mazi.rescu.ExceptionalReturnContentException;

public class CryptopiaBaseResponse<T> {

  private final String message;
  private final T data;

  @JsonCreator
  public CryptopiaBaseResponse(
      @JsonProperty("Success") boolean success,
      @JsonProperty("Message") String message,
      @JsonProperty("Data") T data,
      @JsonProperty("Error") String error) {
    if (!success) {
      throw new ExceptionalReturnContentException("Success set to false in response");
    }
    if (StringUtils.isNotEmpty(error)) {
      throw new ExceptionalReturnContentException("Error field not empty in repsonse");
    }
    this.message = message;
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }

  @Override
  public String toString() {
    return "CryptopiaBaseResponse{"
        + ", message='"
        + message
        + '\''
        + ", data="
        + data
        + '\''
        + '}';
  }
}
