package org.knowm.xchange.poloniex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"asks", "bids", "isFrozen"})
public class PoloniexDepth {

  @JsonProperty("asks")
  private List<List<BigDecimal>> asks = new ArrayList<List<BigDecimal>>();

  @JsonProperty("bids")
  private List<List<BigDecimal>> bids = new ArrayList<List<BigDecimal>>();

  @JsonProperty("isFrozen")
  private String isFrozen;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("asks")
  public List<List<BigDecimal>> getAsks() {

    return asks;
  }

  @JsonProperty("asks")
  public void setAsks(List<List<BigDecimal>> asks) {

    this.asks = asks;
  }

  @JsonProperty("bids")
  public List<List<BigDecimal>> getBids() {

    return bids;
  }

  @JsonProperty("bids")
  public void setBids(List<List<BigDecimal>> bids) {

    this.bids = bids;
  }

  @JsonProperty("isFrozen")
  public String getIsFrozen() {

    return isFrozen;
  }

  @JsonProperty("isFrozen")
  public void setIsFrozen(String isFrozen) {

    this.isFrozen = isFrozen;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {

    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {

    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {

    return "PoloniexDepth [asks="
        + asks
        + ", bids="
        + bids
        + ", isFrozen="
        + isFrozen
        + ", additionalProperties="
        + additionalProperties
        + "]";
  }
}
