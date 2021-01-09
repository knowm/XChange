package org.knowm.xchange.ftx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FtxResponse<V> {

  private final boolean success;
  private final V result;

  @JsonIgnore private final boolean hasMoreData;

  public FtxResponse(
      @JsonProperty("success") boolean success,
      @JsonProperty("result") V result,
      @JsonProperty("hasMoreData") boolean hasMoreData) {
    this.success = success;
    this.result = result;
    this.hasMoreData = hasMoreData;
  }

  public boolean isSuccess() {
    return success;
  }

  public V getResult() {
    return result;
  }

  public boolean isHasMoreData() {
    return hasMoreData;
  }

  @Override
  public String toString() {
    return "FtxResponse{"
        + "success="
        + success
        + ", result="
        + result
        + ", hasMoreData="
        + hasMoreData
        + '}';
  }
}
