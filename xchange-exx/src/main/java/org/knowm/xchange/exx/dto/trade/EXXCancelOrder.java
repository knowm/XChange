package org.knowm.xchange.exx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EXXCancelOrder {

  @JsonProperty("code")
  private int code;

  @JsonProperty("message")
  private String message;

  /** No args constructor for use in serialization */
  public EXXCancelOrder() {}

  /**
   * @param id
   * @param message
   * @param code
   */
  public EXXCancelOrder(int code, String message) {
    super();
    this.code = code;
    this.message = message;
  }

  @JsonProperty("code")
  public int getCode() {
    return code;
  }

  @JsonProperty("code")
  public void setCode(int code) {
    this.code = code;
  }

  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  @JsonProperty("message")
  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("code", code).append("message", message).toString();
  }
}
