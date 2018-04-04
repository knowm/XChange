package org.knowm.xchange.btcturk.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

/** Created by semihunaldi on 26/11/2017 */
public final class BTCTurkOHLC {
  private final Date time;
  private final BigDecimal open;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal close;
  private final BigDecimal volume;
  private final BigDecimal average;
  private final BigDecimal dailyChangeAmount;
  private final BigDecimal dailyChangePercentage;

  public BTCTurkOHLC(
      @JsonProperty("Time") Date time,
      @JsonProperty("Open") BigDecimal open,
      @JsonProperty("High") BigDecimal high,
      @JsonProperty("Low") BigDecimal low,
      @JsonProperty("Close") BigDecimal close,
      @JsonProperty("Volume") BigDecimal volume,
      @JsonProperty("Average") BigDecimal average,
      @JsonProperty("DailyChangeAmount") BigDecimal dailyChangeAmount,
      @JsonProperty("DailyChangePercentage") BigDecimal dailyChangePercentage) {
    this.time = time;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
    this.average = average;
    this.dailyChangeAmount = dailyChangeAmount;
    this.dailyChangePercentage = dailyChangePercentage;
  }

  public Date getTime() {
    return time;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getAverage() {
    return average;
  }

  public BigDecimal getDailyChangeAmount() {
    return dailyChangeAmount;
  }

  public BigDecimal getDailyChangePercentage() {
    return dailyChangePercentage;
  }

  @Override
  public String toString() {
    return "BTCTurkOHLC {"
        + "time="
        + time
        + ", open="
        + open
        + ", high="
        + high
        + ", low="
        + low
        + ", close="
        + close
        + ", volume="
        + volume
        + ", average="
        + average
        + ", dailyChangeAmount="
        + dailyChangeAmount
        + ", dailyChangePercentage="
        + dailyChangePercentage
        + '}';
  }
}
