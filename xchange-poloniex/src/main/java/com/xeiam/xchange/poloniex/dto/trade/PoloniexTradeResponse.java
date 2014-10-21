package com.xeiam.xchange.poloniex.dto.trade;

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
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "orderNumber", "resultingTrades" })
public class PoloniexTradeResponse {

  @JsonProperty("orderNumber")
  private Integer orderNumber;
  @JsonProperty("resultingTrades")
  private List<PoloniexPublicTrade> resultingTrades = new ArrayList<PoloniexPublicTrade>();
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("orderNumber")
  public Integer getOrderNumber() {

    return orderNumber;
  }

  @JsonProperty("orderNumber")
  public void setOrderNumber(Integer orderNumber) {

    this.orderNumber = orderNumber;
  }

  @JsonProperty("resultingTrades")
  public List<PoloniexPublicTrade> getPoloniexPublicTrades() {

    return resultingTrades;
  }

  @JsonProperty("resultingTrades")
  public void setPoloniexPublicTrades(List<PoloniexPublicTrade> resultingTrades) {

    this.resultingTrades = resultingTrades;
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
