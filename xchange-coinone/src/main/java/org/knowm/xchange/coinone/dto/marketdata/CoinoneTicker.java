package org.knowm.xchange.coinone.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author interwater */
public class CoinoneTicker {

  private final String result;
  private final String errorCode;
  private final BigDecimal volume;
  private final BigDecimal last;
  private final BigDecimal yesterday_last;
  private final String timestamp;
  private final BigDecimal yesterday_low;
  private final BigDecimal high;
  private final String currency;
  private final BigDecimal low;
  private final BigDecimal yesterday_first;
  private final BigDecimal yesterday_volume;
  private final BigDecimal yesterday_high;
  private final BigDecimal first;

  public CoinoneTicker(
      @JsonProperty("result") String result,
      @JsonProperty("errorCode") String errorCode,
      @JsonProperty("volume") String volume,
      @JsonProperty("last") String last,
      @JsonProperty("yesterday_last") String yesterday_last,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("yesterday_low") String yesterday_low,
      @JsonProperty("high") String high,
      @JsonProperty("currency") String currency,
      @JsonProperty("low") String low,
      @JsonProperty("yesterday_first") String yesterday_first,
      @JsonProperty("yesterday_volume") String yesterday_volume,
      @JsonProperty("yesterday_high") String yesterday_high,
      @JsonProperty("first") String first) {
    this.result = result;
    this.errorCode = errorCode;
    this.volume = new BigDecimal(volume);
    this.last = new BigDecimal(last);
    this.yesterday_last = new BigDecimal(yesterday_last);
    this.timestamp = timestamp;
    this.yesterday_low = new BigDecimal(yesterday_low);
    this.high = new BigDecimal(high);
    this.currency = currency;
    this.low = new BigDecimal(low);
    this.yesterday_first = new BigDecimal(yesterday_first);
    this.yesterday_volume = new BigDecimal(yesterday_volume);
    this.yesterday_high = new BigDecimal(yesterday_high);
    this.first = new BigDecimal(first);
  }

  public String getResult() {
    return result;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getYesterday_last() {
    return yesterday_last;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public BigDecimal getYesterday_low() {
    return yesterday_low;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getYesterday_first() {
    return yesterday_first;
  }

  public BigDecimal getYesterday_volume() {
    return yesterday_volume;
  }

  public BigDecimal getYesterday_high() {
    return yesterday_high;
  }

  public BigDecimal getFirst() {
    return first;
  }
}
