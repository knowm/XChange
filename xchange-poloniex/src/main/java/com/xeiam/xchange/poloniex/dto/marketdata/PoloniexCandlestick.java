package com.xeiam.xchange.poloniex.dto.marketdata;

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
@JsonPropertyOrder({ "date", "high", "low", "open", "close", "volume", "quoteVolume", "weightedAverage" })
public class PoloniexCandlestick {

  @JsonProperty("date")
  private Long date;
  @JsonProperty("high")
  private BigDecimal high;
  @JsonProperty("low")
  private BigDecimal low;
  @JsonProperty("open")
  private BigDecimal open;
  @JsonProperty("close")
  private BigDecimal close;
  @JsonProperty("volume")
  private BigDecimal volume;
  @JsonProperty("quoteVolume")
  private BigDecimal quoteVolume;
  @JsonProperty("weightedAverage")
  private BigDecimal weightedAverage;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("date")
  public Long getDate() {

    return date;
  }

  @JsonProperty("date")
  public void setDate(Long date) {

    this.date = date;
  }

  @JsonProperty("high")
  public BigDecimal getHigh() {

    return high;
  }

  @JsonProperty("high")
  public void setHigh(BigDecimal high) {

    this.high = high;
  }

  @JsonProperty("low")
  public BigDecimal getLow() {

    return low;
  }

  @JsonProperty("low")
  public void setLow(BigDecimal low) {

    this.low = low;
  }

  @JsonProperty("open")
  public BigDecimal getOpen() {

    return open;
  }

  @JsonProperty("open")
  public void setOpen(BigDecimal open) {

    this.open = open;
  }

  @JsonProperty("close")
  public BigDecimal getClose() {

    return close;
  }

  @JsonProperty("close")
  public void setClose(BigDecimal close) {

    this.close = close;
  }

  @JsonProperty("volume")
  public BigDecimal getVolume() {

    return volume;
  }

  @JsonProperty("volume")
  public void setVolume(BigDecimal volume) {

    this.volume = volume;
  }

  @JsonProperty("quoteVolume")
  public BigDecimal getQuoteVolume() {

    return quoteVolume;
  }

  @JsonProperty("quoteVolume")
  public void setQuoteVolume(BigDecimal quoteVolume) {

    this.quoteVolume = quoteVolume;
  }

  @JsonProperty("weightedAverage")
  public BigDecimal getWeightedAverage() {

    return weightedAverage;
  }

  @JsonProperty("weightedAverage")
  public void setWeightedAverage(BigDecimal weightedAverage) {

    this.weightedAverage = weightedAverage;
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