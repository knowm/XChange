package org.knowm.xchange.kucoin;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import si.mazi.rescu.HttpStatusExceptionSupport;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "msg", "success", "timestamp"})
public class KucoinException extends HttpStatusExceptionSupport {

  private static final long serialVersionUID = 1L;

  @JsonProperty("code")
  private String code;
  @JsonProperty("msg")
  private String msg;
  @JsonProperty("success")
  private boolean success;
  @JsonProperty("timestamp")
  private long timestamp;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @Override
  public String getMessage() {
    return String.format("%s - %s (HTTP status code: %d)", code, msg, getHttpStatusCode());
  }
  
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  @JsonProperty("code")
  public void setCode(String code) {
    this.code = code;
  }

  @JsonProperty("msg")
  public String getMsg() {
    return msg;
  }

  @JsonProperty("msg")
  public void setMsg(String msg) {
    this.msg = msg;
  }

  @JsonProperty("success")
  public boolean isSuccess() {
    return success;
  }

  @JsonProperty("success")
  public void setSuccess(boolean success) {
    this.success = success;
  }

  @JsonProperty("timestamp")
  public long getTimestamp() {
    return timestamp;
  }

  @JsonProperty("timestamp")
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }
}
