package org.knowm.xchange.abucoins.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * POJO representing the output JSON for the Abucoins <code>GET /products/&lt;product-id&gt;/ticker
 * </code> endpoint. Example: <code><pre>
 * {
 *     "trade_id": "553612",
 *     "price": "14160.85",
 *     "size": "0.00053",
 *     "bid": "14140.00000000",
 *     "ask": "14181.70000000",
 *     "volume": "1.09596639",
 *     "time": "2017-09-21T10:26:58Z"
 * }
 * </pre></code>
 *
 * @author bryant_harris
 */
public class AbucoinsTicker {
  /** identifier of the last trade */
  String tradeID;

  /** last price */
  BigDecimal price;

  /** size of the last trade */
  BigDecimal size;

  /** the best bid */
  BigDecimal bid;

  /** the best ask */
  BigDecimal ask;

  /** 24 hour volume */
  BigDecimal volume;

  /** time in UTC */
  String time;

  /**
   * Constructor
   *
   * @param tradeID identifier of the last trade
   * @param price last price
   * @param size size of the last trade
   * @param bid the best bid
   * @param ask the best ask
   * @param volume 24 hour volume
   * @param time time in UTC
   */
  public AbucoinsTicker(
      @JsonProperty("trade_id") String tradeID,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("time") String time) {
    this.tradeID = tradeID;
    this.price = price;
    this.size = size;
    this.bid = bid;
    this.ask = ask;
    this.volume = volume;
    this.time = time;
  }

  /** identifier of the last trade */
  public String getTradeID() {
    return tradeID;
  }

  /** last price */
  public BigDecimal getPrice() {
    return price;
  }

  /** size of the last trade */
  public BigDecimal getSize() {
    return size;
  }

  /** the best bid */
  public BigDecimal getBid() {
    return bid;
  }

  /** the best ask */
  public BigDecimal getAsk() {
    return ask;
  }

  /** 24 hour volume */
  public BigDecimal getVolume() {
    return volume;
  }

  /** time in UTC */
  public String getTime() {
    return time;
  }

  @Override
  public String toString() {
    return "AbucoinsTicker [trade_id="
        + tradeID
        + ", price="
        + price
        + ", size="
        + size
        + ", bid="
        + bid
        + ", ask="
        + ask
        + ", volume="
        + volume
        + ", time="
        + time
        + "]";
  }
}
