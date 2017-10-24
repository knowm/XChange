package org.knowm.xchange.poloniex.dto.marketdata;

import java.math.BigDecimal;
import java.util.HashMap;
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
@JsonPropertyOrder({"last", "lowestAsk", "highestBid", "percentChange", "baseVolume", "quoteVolume"})
public class PoloniexMarketData {

  @JsonProperty("high24hr")
  private BigDecimal high24hr;
  @JsonProperty("low24hr")
  private BigDecimal low24hr;
  @JsonProperty("last")
  private BigDecimal last;
  @JsonProperty("lowestAsk")
  private BigDecimal lowestAsk;
  @JsonProperty("highestBid")
  private BigDecimal highestBid;
  @JsonProperty("percentChange")
  private BigDecimal percentChange;
  @JsonProperty("baseVolume")
  private BigDecimal baseVolume;
  @JsonProperty("quoteVolume")
  private BigDecimal quoteVolume;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public BigDecimal getHigh24hr() {
    return high24hr;
  }

  public void setHigh24hr(BigDecimal high24hr) {
    this.high24hr = high24hr;
  }

  public void setLow24hr(BigDecimal low24hr) {
    this.low24hr = low24hr;
  }

  public BigDecimal getLow24hr() {
    return low24hr;
  }

  @JsonProperty("last")
  public BigDecimal getLast() {

    return last;
  }

  @JsonProperty("last")
  public void setLast(BigDecimal last) {

    this.last = last;
  }

  @JsonProperty("lowestAsk")
  public BigDecimal getLowestAsk() {

    return lowestAsk;
  }

  @JsonProperty("lowestAsk")
  public void setLowestAsk(BigDecimal lowestAsk) {

    this.lowestAsk = lowestAsk;
  }

  @JsonProperty("highestBid")
  public BigDecimal getHighestBid() {

    return highestBid;
  }

  @JsonProperty("highestBid")
  public void setHighestBid(BigDecimal highestBid) {

    this.highestBid = highestBid;
  }

  @JsonProperty("percentChange")
  public BigDecimal getPercentChange() {

    return percentChange;
  }

  @JsonProperty("percentChange")
  public void setPercentChange(BigDecimal percentChange) {

    this.percentChange = percentChange;
  }

  @JsonProperty("baseVolume")
  public BigDecimal getBaseVolume() {

    return baseVolume;
  }

  @JsonProperty("baseVolume")
  public void setBaseVolume(BigDecimal baseVolume) {

    this.baseVolume = baseVolume;
  }

  @JsonProperty("quoteVolume")
  public BigDecimal getQuoteVolume() {

    return quoteVolume;
  }

  @JsonProperty("quoteVolume")
  public void setQuoteVolume(BigDecimal quoteVolume) {

    this.quoteVolume = quoteVolume;
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

    return "PoloniexMarketData [last=" + last + ", lowestAsk=" + lowestAsk + ", highestBid=" + highestBid + ", percentChange=" + percentChange
        + ", baseVolume=" + baseVolume + ", quoteVolume=" + quoteVolume + ", additionalProperties=" + additionalProperties + "]";
  }

}
