package org.knowm.xchange.coinone.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author interwater */
public class CoinoneTickerData {
  private BigDecimal bid;
  private BigDecimal ask;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal last;
  private BigDecimal volume;
  private long timestamp;
  /**
   * @param opening_price
   * @param closing_price
   * @param min_price
   * @param max_price
   * @param average_price
   * @param units_traded
   * @param volume_1day
   * @param volume_7day
   * @param buy_price
   * @param sell_price
   * @param date
   */
  public CoinoneTickerData(
      @JsonProperty("opening_price") String opening_price,
      @JsonProperty("closing_price") String closing_price,
      @JsonProperty("min_price") String min_price,
      @JsonProperty("max_price") String max_price,
      @JsonProperty("average_price") String average_price,
      @JsonProperty("units_traded") String units_traded,
      @JsonProperty("volume_1day") String volume_1day,
      @JsonProperty("volume_7day") String volume_7day,
      @JsonProperty("buy_price") String buy_price,
      @JsonProperty("sell_price") String sell_price,
      @JsonProperty("date") String date) {

    this.bid = new BigDecimal(sell_price);
    this.ask = new BigDecimal(buy_price);
    this.last = new BigDecimal(average_price);
    this.volume = new BigDecimal(volume_1day);
    this.high = new BigDecimal(max_price);
    this.low = new BigDecimal(min_price);
    this.timestamp = Long.valueOf(date);
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public long getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {

    return "KorbitTicker [bid="
        + bid
        + ", ask="
        + ask
        + ", low="
        + low
        + ", high="
        + high
        + ", last="
        + last
        + ", timestamp="
        + timestamp
        + "]";
  }
}
