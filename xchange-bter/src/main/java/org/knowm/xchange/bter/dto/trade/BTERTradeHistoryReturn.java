package org.knowm.xchange.bter.dto.trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.knowm.xchange.bter.dto.BTERBaseResponse;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"result", "trades", "msg"})
public class BTERTradeHistoryReturn extends BTERBaseResponse {

  @JsonProperty("result")
  private Boolean result;
  @JsonProperty("trades")
  private List<BTERTrade> trades = new ArrayList<BTERTrade>();
  @JsonProperty("msg")
  private String msg;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  protected BTERTradeHistoryReturn(@JsonProperty("result") boolean result, @JsonProperty("trades") List<BTERTrade> trades,
      @JsonProperty("msg") String message) {

    super(result, message);
    this.trades = trades;
  }

  @JsonProperty("result")
  public Boolean getResult() {

    return result;
  }

  @JsonProperty("result")
  public void setResult(Boolean result) {

    this.result = result;
  }

  @JsonProperty("trades")
  public List<BTERTrade> getTrades() {

    return trades;
  }

  @JsonProperty("trades")
  public void setTrades(List<BTERTrade> trades) {

    this.trades = trades;
  }

  @JsonProperty("msg")
  public String getMsg() {

    return msg;
  }

  @JsonProperty("msg")
  public void setMsg(String msg) {

    this.msg = msg;
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