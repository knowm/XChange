package org.knowm.xchange.gateio.dto.trade;

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
import org.knowm.xchange.gateio.dto.GateioBaseResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"result", "trades", "msg"})
public class GateioTradeHistoryReturn extends GateioBaseResponse {

  @JsonProperty("result")
  private Boolean result;

  @JsonProperty("trades")
  private List<GateioTrade> trades = new ArrayList<GateioTrade>();

  @JsonProperty("msg")
  private String msg;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  protected GateioTradeHistoryReturn(
      @JsonProperty("result") boolean result,
      @JsonProperty("trades") List<GateioTrade> trades,
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
  public List<GateioTrade> getTrades() {

    return trades;
  }

  @JsonProperty("trades")
  public void setTrades(List<GateioTrade> trades) {

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
