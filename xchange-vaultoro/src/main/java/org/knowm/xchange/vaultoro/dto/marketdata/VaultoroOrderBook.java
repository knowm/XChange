package org.knowm.xchange.vaultoro.dto.marketdata;

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
@JsonPropertyOrder({"b", "s"})
public class VaultoroOrderBook {

  @JsonProperty("b")
  private List<VaultoroOrder> b = new ArrayList<VaultoroOrder>();
  @JsonProperty("s")
  private List<VaultoroOrder> s = new ArrayList<VaultoroOrder>();
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The b
   */
  @JsonProperty("b")
  public List<VaultoroOrder> getBuys() {

    return b;
  }

  /**
   * @param b The b
   */
  @JsonProperty("b")
  public void setB(List<VaultoroOrder> b) {

    this.b = b;
  }

  /**
   * @return The s
   */
  @JsonProperty("s")
  public List<VaultoroOrder> getSells() {

    return s;
  }

  /**
   * @param s The s
   */
  @JsonProperty("s")
  public void setS(List<VaultoroOrder> s) {

    this.s = s;
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

    return "VaultoroOrderBook [b=" + b + ", s=" + s + ", additionalProperties=" + additionalProperties + "]";
  }

}