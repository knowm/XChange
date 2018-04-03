package org.knowm.xchange.bibox.dto.trade;

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
@JsonPropertyOrder({"pair", "update_time", "asks", "bids"})
public class BiboxOrderBook {

  @JsonProperty("pair")
  private String pair;

  @JsonProperty("update_time")
  private long updateTime;

  @JsonProperty("asks")
  private List<BiboxOrderBookEntry> asks = new ArrayList<BiboxOrderBookEntry>();

  @JsonProperty("bids")
  private List<BiboxOrderBookEntry> bids = new ArrayList<BiboxOrderBookEntry>();

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** @return The pair */
  @JsonProperty("pair")
  public String getPair() {
    return pair;
  }

  /** @param pair The pair */
  @JsonProperty("pair")
  public void setPair(String pair) {
    this.pair = pair;
  }

  /** @return The updateTime */
  @JsonProperty("update_time")
  public long getUpdateTime() {
    return updateTime;
  }

  /** @param updateTime The update_time */
  @JsonProperty("update_time")
  public void setUpdateTime(long updateTime) {
    this.updateTime = updateTime;
  }

  /** @return The asks */
  @JsonProperty("asks")
  public List<BiboxOrderBookEntry> getAsks() {
    return asks;
  }

  /** @param asks The asks */
  @JsonProperty("asks")
  public void setAsks(List<BiboxOrderBookEntry> asks) {
    this.asks = asks;
  }

  /** @return The bids */
  @JsonProperty("bids")
  public List<BiboxOrderBookEntry> getBids() {
    return bids;
  }

  /** @param bids The bids */
  @JsonProperty("bids")
  public void setBids(List<BiboxOrderBookEntry> bids) {
    this.bids = bids;
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
