package org.knowm.xchange.bitbay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** @author Z. Dolezal */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitbayBaseResponse {

  private final boolean success;
  private final int code;
  private final String message;

  public BitbayBaseResponse(
      @JsonProperty("success") boolean success,
      @JsonProperty("code") int code,
      @JsonProperty("message") String errorMsg) {
    this.success = success;
    this.code = code;
    this.message = errorMsg;
  }

  public boolean isSuccess() {
    return success;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
