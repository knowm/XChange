package org.knowm.xchange.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"success", "code", "msg", "timestamp", "data"})
public class KucoinResponse<D> extends KucoinSimpleResponse<D> {

  @JsonProperty("msg")
  private String msg;

  @JsonProperty("timestamp")
  private Long timestamp;

  public KucoinResponse() {}

  /**
   * @param msg
   * @param code
   * @param data
   * @param success
   * @param timestamp
   */
  public KucoinResponse(Boolean success, String code, String msg, Long timestamp, D data) {
    super(success, code, data);
    this.msg = msg;
    this.timestamp = timestamp;
  }

  /** @return The msg */
  @JsonProperty("msg")
  public String getMsg() {
    return msg;
  }

  /** @param msg The msg */
  @JsonProperty("msg")
  public void setMsg(String msg) {
    this.msg = msg;
  }

  /** @return The timestamp */
  @JsonProperty("timestamp")
  public Long getTimestamp() {
    return timestamp;
  }

  /** @param timestamp The timestamp */
  @JsonProperty("timestamp")
  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }
}
