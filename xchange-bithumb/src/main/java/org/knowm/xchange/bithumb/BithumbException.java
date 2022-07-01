package org.knowm.xchange.bithumb;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class BithumbException extends HttpStatusExceptionSupport {

  private String status;
  private String message;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public BithumbException() {}

  public BithumbException(String status, String message) {
    super(message);
    this.status = status;
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @JsonProperty("message")
  public void setMessage(String message) {
    this.message = message;
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
