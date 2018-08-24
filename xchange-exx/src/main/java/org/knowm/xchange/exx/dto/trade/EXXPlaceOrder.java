package org.knowm.xchange.exx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EXXPlaceOrder {

  @JsonProperty("code")
  private int code;

  @JsonProperty("message")
  private String message;

  @JsonProperty("id")
  private String id;

  /** No args constructor for use in serialization */
  public EXXPlaceOrder() {}

  /**
   * @param id
   * @param message
   * @param code
   */
  public EXXPlaceOrder(int code, String message, String id) {
    super();
    this.code = code;
    this.message = message;
    this.id = id;
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

  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("code", code)
        .append("message", message)
        .append("id", id)
        .toString();
  }
}
