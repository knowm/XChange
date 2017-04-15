package org.knowm.xchange.ccex.dto.ticker;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"high", "low", "avg", "lastbuy", "lastsell", "buy", "sell", "lastprice", "buysupport", "updated"})
public class Ticker {

  @JsonProperty("high")
  private Double high;
  @JsonProperty("low")
  private Double low;
  @JsonProperty("avg")
  private Double avg;
  @JsonProperty("lastbuy")
  private Double lastbuy;
  @JsonProperty("lastsell")
  private Double lastsell;
  @JsonProperty("buy")
  private Double buy;
  @JsonProperty("sell")
  private Double sell;
  @JsonProperty("lastprice")
  private Double lastprice;
  @JsonProperty("buysupport")
  private Double buysupport;
  @JsonProperty("updated")
  private Integer updated;

  /**
   * No args constructor for use in serialization
   */
  public Ticker() {
  }

  /**
   * @param lastbuy
   * @param buysupport
   * @param lastprice
   * @param updated
   * @param sell
   * @param buy
   * @param lastsell
   * @param high
   * @param avg
   * @param low
   */
  public Ticker(Double high, Double low, Double avg, Double lastbuy, Double lastsell, Double buy, Double sell, Double lastprice, Double buysupport,
      Integer updated) {
    this.high = high;
    this.low = low;
    this.avg = avg;
    this.lastbuy = lastbuy;
    this.lastsell = lastsell;
    this.buy = buy;
    this.sell = sell;
    this.lastprice = lastprice;
    this.buysupport = buysupport;
    this.updated = updated;
  }

  /**
   * @return The high
   */
  @JsonProperty("high")
  public Double getHigh() {
    return high;
  }

  /**
   * @param high The high
   */
  @JsonProperty("high")
  public void setHigh(Double high) {
    this.high = high;
  }

  public Ticker withHigh(Double high) {
    this.high = high;
    return this;
  }

  /**
   * @return The low
   */
  @JsonProperty("low")
  public Double getLow() {
    return low;
  }

  /**
   * @param low The low
   */
  @JsonProperty("low")
  public void setLow(Double low) {
    this.low = low;
  }

  public Ticker withLow(Double low) {
    this.low = low;
    return this;
  }

  /**
   * @return The avg
   */
  @JsonProperty("avg")
  public Double getAvg() {
    return avg;
  }

  /**
   * @param avg The avg
   */
  @JsonProperty("avg")
  public void setAvg(Double avg) {
    this.avg = avg;
  }

  public Ticker withAvg(Double avg) {
    this.avg = avg;
    return this;
  }

  /**
   * @return The lastbuy
   */
  @JsonProperty("lastbuy")
  public Double getLastbuy() {
    return lastbuy;
  }

  /**
   * @param lastbuy The lastbuy
   */
  @JsonProperty("lastbuy")
  public void setLastbuy(Double lastbuy) {
    this.lastbuy = lastbuy;
  }

  public Ticker withLastbuy(Double lastbuy) {
    this.lastbuy = lastbuy;
    return this;
  }

  /**
   * @return The lastsell
   */
  @JsonProperty("lastsell")
  public Double getLastsell() {
    return lastsell;
  }

  /**
   * @param lastsell The lastsell
   */
  @JsonProperty("lastsell")
  public void setLastsell(Double lastsell) {
    this.lastsell = lastsell;
  }

  public Ticker withLastsell(Double lastsell) {
    this.lastsell = lastsell;
    return this;
  }

  /**
   * @return The buy
   */
  @JsonProperty("buy")
  public Double getBuy() {
    return buy;
  }

  /**
   * @param buy The buy
   */
  @JsonProperty("buy")
  public void setBuy(Double buy) {
    this.buy = buy;
  }

  public Ticker withBuy(Double buy) {
    this.buy = buy;
    return this;
  }

  /**
   * @return The sell
   */
  @JsonProperty("sell")
  public Double getSell() {
    return sell;
  }

  /**
   * @param sell The sell
   */
  @JsonProperty("sell")
  public void setSell(Double sell) {
    this.sell = sell;
  }

  public Ticker withSell(Double sell) {
    this.sell = sell;
    return this;
  }

  /**
   * @return The lastprice
   */
  @JsonProperty("lastprice")
  public Double getLastprice() {
    return lastprice;
  }

  /**
   * @param lastprice The lastprice
   */
  @JsonProperty("lastprice")
  public void setLastprice(Double lastprice) {
    this.lastprice = lastprice;
  }

  public Ticker withLastprice(Double lastprice) {
    this.lastprice = lastprice;
    return this;
  }

  /**
   * @return The buysupport
   */
  @JsonProperty("buysupport")
  public Double getBuysupport() {
    return buysupport;
  }

  /**
   * @param buysupport The buysupport
   */
  @JsonProperty("buysupport")
  public void setBuysupport(Double buysupport) {
    this.buysupport = buysupport;
  }

  public Ticker withBuysupport(Double buysupport) {
    this.buysupport = buysupport;
    return this;
  }

  /**
   * @return The updated
   */
  @JsonProperty("updated")
  public Integer getUpdated() {
    return updated;
  }

  /**
   * @param updated The updated
   */
  @JsonProperty("updated")
  public void setUpdated(Integer updated) {
    this.updated = updated;
  }

  public Ticker withUpdated(Integer updated) {
    this.updated = updated;
    return this;
  }

}