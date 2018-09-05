package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.bittrex.BittrexUtils;

public class BittrexChartData {
  private final Date timeStamp;
  private final BigDecimal open;
  private final BigDecimal close;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;
  private final BigDecimal baseVolume;

  public BittrexChartData(
      @JsonProperty("T") String timeStamp,
      @JsonProperty("O") BigDecimal open,
      @JsonProperty("C") BigDecimal close,
      @JsonProperty("H") BigDecimal high,
      @JsonProperty("L") BigDecimal low,
      @JsonProperty("V") BigDecimal volume,
      @JsonProperty("BV") BigDecimal baseVolume) {
    this.timeStamp = BittrexUtils.toDate(timeStamp);
    this.open = open;
    this.close = close;
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.baseVolume = baseVolume;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getBaseVolume() {
    return baseVolume;
  }

  @Override
  public String toString() {
    return "BittrexChartData [timeStamp="
        + timeStamp
        + ", open="
        + open
        + ", close="
        + close
        + ", high="
        + high
        + ", low="
        + low
        + ", volume="
        + volume
        + ", baseVolume="
        + baseVolume
        + "]";
  }
}
