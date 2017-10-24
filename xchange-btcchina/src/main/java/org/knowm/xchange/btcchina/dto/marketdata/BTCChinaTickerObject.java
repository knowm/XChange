package org.knowm.xchange.btcchina.dto.marketdata;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaTickerObject implements Serializable {

  private final BigDecimal buy;
  private final BigDecimal high;
  private final BigDecimal last;
  private final BigDecimal low;
  private final BigDecimal sell;
  private final BigDecimal vol;
  private final long date;
  private final BigDecimal vwap;
  private final BigDecimal prevClose;
  private final BigDecimal open;

  /**
   * Constructor
   *
   * @param buy Latest bid price.
   * @param sell Latest ask price.
   * @param high Highest price in last 24h.
   * @param low Lowest price in last 24h.
   * @param vol Total BTC(or LTC) volume in last 24h.
   * @param last Last successful trade price
   * @param date Last update timestamp.
   * @param vwap Today's average filled price.
   * @param prevClose Yesterday's closed price.
   */
  public BTCChinaTickerObject(@JsonProperty("buy") BigDecimal buy, @JsonProperty("sell") BigDecimal sell, @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low, @JsonProperty("vol") BigDecimal vol, @JsonProperty("last") BigDecimal last,
      @JsonProperty("date") long date, @JsonProperty("vwap") BigDecimal vwap, @JsonProperty("prev_close") BigDecimal prevClose,
      @JsonProperty("open") BigDecimal open) {

    this.high = high;
    this.low = low;
    this.vol = vol;
    this.last = last;
    this.buy = buy;
    this.sell = sell;
    this.date = date;
    this.vwap = vwap;
    this.prevClose = prevClose;
    this.open = open;
  }

  public BigDecimal getBuy() {

    return this.buy;
  }

  public BigDecimal getHigh() {

    return this.high;
  }

  public BigDecimal getLast() {

    return this.last;
  }

  public BigDecimal getLow() {

    return this.low;
  }

  public BigDecimal getSell() {

    return this.sell;
  }

  public BigDecimal getVol() {

    return this.vol;
  }

  public long getDate() {

    return date;
  }

  /**
   * Returns today's average filled price.
   *
   * @return today's average filled price.
   * @since <a href="http://btcchina.org/api-market-data-documentation-en#data_api_v131" >Data API v1.3.1</a>
   */
  public BigDecimal getVwap() {

    return vwap;
  }

  /**
   * Returns yesterday's closed price.
   *
   * @return Yesterday's closed price.
   * @since <a href="http://btcchina.org/api-market-data-documentation-en#data_api_v131" >Data API v1.3.1</a>
   */
  public BigDecimal getPrevClose() {

    return prevClose;
  }

  /**
   * Returns today's opening price.
   *
   * @return Today's opening price.
   * @since <a href="http://btcchina.org/api-market-data-documentation-en#data_api_v132" >Data API v1.3.2</a>
   */
  public BigDecimal getOpen() {

    return open;
  }

  @Override
  public String toString() {

    return "BTCChinaTicker [last=" + last + ", high=" + high + ", low=" + low + ", buy=" + buy + ", sell=" + sell + ", vol=" + vol + ", date=" + date
        + ", vwap=" + vwap + ", preClose=" + prevClose + ", open=" + open + "]";

  }
}
