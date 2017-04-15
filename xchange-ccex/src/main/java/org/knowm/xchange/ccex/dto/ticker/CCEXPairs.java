package org.knowm.xchange.ccex.dto.ticker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"pairs"})
public class CCEXPairs {

  @JsonProperty("pairs")
  private List<String> pairs = new ArrayList<String>();
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * No args constructor for use in serialization
   */
  public CCEXPairs() {
  }

  /**
   * @param pairs
   */
  public CCEXPairs(List<String> pairs) {
    this.pairs = pairs;
  }

  /**
   * @return The pairs
   */
  @JsonProperty("pairs")
  public List<String> getPairs() {
    return pairs;
  }

  /**
   * @param pairs The pairs
   */
  @JsonProperty("pairs")
  public void setPairs(List<String> pairs) {
    this.pairs = pairs;
  }

  public CCEXPairs withPairs(List<String> pairs) {
    this.pairs = pairs;
    return this;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

  public CCEXPairs withAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
    return this;
  }

}