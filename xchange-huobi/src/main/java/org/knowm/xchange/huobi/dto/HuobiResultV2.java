package org.knowm.xchange.huobi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiResultV2<V> {

  private final String code;
  private final String message;
  private final V result;

  @JsonCreator
  public HuobiResultV2(
      @JsonProperty("code") String code,
      @JsonProperty("message") String message,
      V result) {
    this.code = code;
    this.message = message;
    this.result = result;
  }

  public boolean isSuccess() {
    return getCode().equals("200");
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public V getResult() {
    return result;
  }

  @Override
  public String toString() {
    return String.format(
        "HuobiResult [%s, %s]", code, isSuccess() ? getResult().toString() : getMessage());
  }

}
