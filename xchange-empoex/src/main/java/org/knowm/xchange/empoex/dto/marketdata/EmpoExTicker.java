package org.knowm.xchange.empoex.dto.marketdata;

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
@JsonPropertyOrder({"pairname", "last", "base_volume_24hr", "low", "high", "bid", "ask", "open_buy_volume", "open_sell_volume",
                       "open_buy_volume_base", "open_sell_volume_base", "change"})
public class EmpoExTicker {

  @JsonProperty("pairname")
  private String pairname;
  @JsonProperty("last")
  private String last;
  @JsonProperty("base_volume_24hr")
  private String baseVolume24hr;
  @JsonProperty("low")
  private String low;
  @JsonProperty("high")
  private String high;
  @JsonProperty("bid")
  private String bid;
  @JsonProperty("ask")
  private String ask;
  @JsonProperty("open_buy_volume")
  private String openBuyVolume;
  @JsonProperty("open_sell_volume")
  private String openSellVolume;
  @JsonProperty("open_buy_volume_base")
  private String openBuyVolumeBase;
  @JsonProperty("open_sell_volume_base")
  private String openSellVolumeBase;
  @JsonProperty("change")
  private String change;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The pairname
   */
  @JsonProperty("pairname")
  public String getPairname() {
    return pairname;
  }

  /**
   * @param pairname The pairname
   */
  @JsonProperty("pairname")
  public void setPairname(String pairname) {
    this.pairname = pairname;
  }

  /**
   * @return The last
   */
  @JsonProperty("last")
  public String getLast() {
    return last;
  }

  /**
   * @param last The last
   */
  @JsonProperty("last")
  public void setLast(String last) {
    this.last = last;
  }

  /**
   * @return The baseVolume24hr
   */
  @JsonProperty("base_volume_24hr")
  public String getBaseVolume24hr() {
    return baseVolume24hr;
  }

  /**
   * @param baseVolume24hr The base_volume_24hr
   */
  @JsonProperty("base_volume_24hr")
  public void setBaseVolume24hr(String baseVolume24hr) {
    this.baseVolume24hr = baseVolume24hr;
  }

  /**
   * @return The low
   */
  @JsonProperty("low")
  public String getLow() {
    return low;
  }

  /**
   * @param low The low
   */
  @JsonProperty("low")
  public void setLow(String low) {
    this.low = low;
  }

  /**
   * @return The high
   */
  @JsonProperty("high")
  public String getHigh() {
    return high;
  }

  /**
   * @param high The high
   */
  @JsonProperty("high")
  public void setHigh(String high) {
    this.high = high;
  }

  /**
   * @return The bid
   */
  @JsonProperty("bid")
  public String getBid() {
    return bid;
  }

  /**
   * @param bid The bid
   */
  @JsonProperty("bid")
  public void setBid(String bid) {
    this.bid = bid;
  }

  /**
   * @return The ask
   */
  @JsonProperty("ask")
  public String getAsk() {
    return ask;
  }

  /**
   * @param ask The ask
   */
  @JsonProperty("ask")
  public void setAsk(String ask) {
    this.ask = ask;
  }

  /**
   * @return The openBuyVolume
   */
  @JsonProperty("open_buy_volume")
  public String getOpenBuyVolume() {
    return openBuyVolume;
  }

  /**
   * @param openBuyVolume The open_buy_volume
   */
  @JsonProperty("open_buy_volume")
  public void setOpenBuyVolume(String openBuyVolume) {
    this.openBuyVolume = openBuyVolume;
  }

  /**
   * @return The openSellVolume
   */
  @JsonProperty("open_sell_volume")
  public String getOpenSellVolume() {
    return openSellVolume;
  }

  /**
   * @param openSellVolume The open_sell_volume
   */
  @JsonProperty("open_sell_volume")
  public void setOpenSellVolume(String openSellVolume) {
    this.openSellVolume = openSellVolume;
  }

  /**
   * @return The openBuyVolumeBase
   */
  @JsonProperty("open_buy_volume_base")
  public String getOpenBuyVolumeBase() {
    return openBuyVolumeBase;
  }

  /**
   * @param openBuyVolumeBase The open_buy_volume_base
   */
  @JsonProperty("open_buy_volume_base")
  public void setOpenBuyVolumeBase(String openBuyVolumeBase) {
    this.openBuyVolumeBase = openBuyVolumeBase;
  }

  /**
   * @return The openSellVolumeBase
   */
  @JsonProperty("open_sell_volume_base")
  public String getOpenSellVolumeBase() {
    return openSellVolumeBase;
  }

  /**
   * @param openSellVolumeBase The open_sell_volume_base
   */
  @JsonProperty("open_sell_volume_base")
  public void setOpenSellVolumeBase(String openSellVolumeBase) {
    this.openSellVolumeBase = openSellVolumeBase;
  }

  /**
   * @return The change
   */
  @JsonProperty("change")
  public String getChange() {
    return change;
  }

  /**
   * @param change The change
   */
  @JsonProperty("change")
  public void setChange(String change) {
    this.change = change;
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
    return "EmpoExTicker [pairname=" + pairname + ", last=" + last + ", baseVolume24hr=" + baseVolume24hr + ", low=" + low + ", high=" + high
        + ", bid=" + bid + ", ask=" + ask + ", openBuyVolume=" + openBuyVolume + ", openSellVolume=" + openSellVolume + ", openBuyVolumeBase="
        + openBuyVolumeBase + ", openSellVolumeBase=" + openSellVolumeBase + ", change=" + change + ", additionalProperties=" + additionalProperties
        + "]";
  }

}