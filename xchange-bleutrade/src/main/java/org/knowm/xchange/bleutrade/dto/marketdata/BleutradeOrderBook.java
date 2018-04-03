package org.knowm.xchange.bleutrade.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"buy", "sell"})
public class BleutradeOrderBook {

  @JsonProperty("buy")
  private List<BleutradeLevel> buy = new ArrayList<BleutradeLevel>();

  @JsonProperty("sell")
  private List<BleutradeLevel> sell = new ArrayList<BleutradeLevel>();

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** @return The buy */
  @JsonProperty("buy")
  public List<BleutradeLevel> getBuy() {

    return buy;
  }

  /** @param buy The buy */
  @JsonProperty("buy")
  public void setBuy(List<BleutradeLevel> buy) {

    this.buy = buy;
  }

  /** @return The sell */
  @JsonProperty("sell")
  public List<BleutradeLevel> getSell() {

    return sell;
  }

  /** @param sell The sell */
  @JsonProperty("sell")
  public void setSell(List<BleutradeLevel> sell) {

    this.sell = sell;
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

    return "BleutradeOrderBookResult [buy="
        + buy
        + ", sell="
        + sell
        + ", additionalProperties="
        + additionalProperties
        + "]";
  }
}
