package org.knowm.xchange.bitcoinde.dto.account;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"data", "errors", "credits"})
public class BitcoindeAccountWrapper {

  @JsonProperty("data")
  private BitcoindeData data;

  @JsonProperty("errors")
  private List<Object> errors = null;

  @JsonProperty("credits")
  private Integer credits;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public BitcoindeAccountWrapper() {}

  /**
   * @param errors
   * @param data
   * @param credits
   */
  public BitcoindeAccountWrapper(BitcoindeData data, List<Object> errors, Integer credits) {
    super();
    this.data = data;
    this.errors = errors;
    this.credits = credits;
  }

  @JsonProperty("data")
  public BitcoindeData getData() {
    return data;
  }

  @JsonProperty("data")
  public void setData(BitcoindeData data) {
    this.data = data;
  }

  @JsonProperty("errors")
  public List<Object> getErrors() {
    return errors;
  }

  @JsonProperty("errors")
  public void setErrors(List<Object> errors) {
    this.errors = errors;
  }

  @JsonProperty("credits")
  public Integer getCredits() {
    return credits;
  }

  @JsonProperty("credits")
  public void setCredits(Integer credits) {
    this.credits = credits;
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
