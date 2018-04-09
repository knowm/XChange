package org.knowm.xchange.bitmarket.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** @author kpysniak, kfonal */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitMarketBaseResponse<T> {

  private final boolean success;
  private final T data;
  private final int error;
  private final String errorMsg;
  private final BitMarketAPILimit limit;

  /**
   * Constructor
   *
   * @param success
   * @param data
   * @param limit
   * @param error
   * @param errorMsg
   */
  public BitMarketBaseResponse(
      @JsonProperty("success") boolean success,
      @JsonProperty("data") T data,
      @JsonProperty("limit") BitMarketAPILimit limit,
      @JsonProperty("error") int error,
      @JsonProperty("errorMsg") String errorMsg) {

    this.success = success;
    this.data = data;
    this.limit = limit;
    this.error = error;
    this.errorMsg = errorMsg;
  }

  public boolean getSuccess() {

    return success;
  }

  public T getData() {

    return data;
  }

  public BitMarketAPILimit getLimit() {

    return limit;
  }

  public int getError() {

    return error;
  }

  public String getErrorMsg() {

    return errorMsg;
  }
}
