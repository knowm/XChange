package org.knowm.xchange.bx.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BxResult<V> {

  private final V result;
  private final boolean success;
  private final String error;

  @JsonCreator
  protected BxResult(
      V result, @JsonProperty("success") boolean success, @JsonProperty("error") String error) {
    this.result = result;
    this.success = success;
    this.error = error;
  }

  public boolean isSuccess() {
    return success;
  }

  public V getResult() {
    return result;
  }

  public String getError() {
    return error;
  }

  @Override
  public String toString() {
    return "BxResult{"
        + "result="
        + result
        + ", success="
        + success
        + ", error='"
        + error
        + '\''
        + '}';
  }
}
