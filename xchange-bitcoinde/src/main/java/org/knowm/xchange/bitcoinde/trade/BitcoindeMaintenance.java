package org.knowm.xchange.bitcoinde.trade;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

/** @author kaiserfr */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"message", "start", "end"})
public class BitcoindeMaintenance {

  @JsonProperty("message")
  private String message;

  @JsonProperty("start")
  private String start;

  @JsonProperty("end")
  private String end;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public BitcoindeMaintenance() {}

  /**
   * @param message
   * @param start
   * @param end
   */
  public BitcoindeMaintenance(String message, String start, String end) {
    super();
    this.message = message;
    this.start = start;
    this.end = end;
  }

  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  @JsonProperty("message")
  public void setMessage(String message) {
    this.message = message;
  }

  @JsonProperty("start")
  public String getStart() {
    return start;
  }

  @JsonProperty("start")
  public void setStart(String start) {
    this.start = start;
  }

  @JsonProperty("end")
  public String getEnd() {
    return end;
  }

  @JsonProperty("end")
  public void setEnd(String end) {
    this.end = end;
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
