package org.knowm.xchange.ftx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class FtxCandleDto {

  @JsonProperty("open")
  private final BigDecimal open;

  @JsonProperty("close")
  private final BigDecimal close;

  @JsonProperty("high")
  private final BigDecimal high;

  @JsonProperty("low")
  private final BigDecimal low;

  @JsonProperty("volume")
  private final BigDecimal volume;

  @JsonProperty("startTime")
  private final Date startTime;

  @JsonCreator
  public FtxCandleDto(
      @JsonProperty("close") BigDecimal close,
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("startTime") Date startTime) {

    this.close = close;
    this.open = open;
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.startTime = startTime;
  }

  public BigDecimal getOpen() {
    return this.open;
  }

  public BigDecimal getHigh() {
    return this.high;
  }

  public BigDecimal getLow() {
    return this.low;
  }

  public BigDecimal getClose() {
    return this.close;
  }

  public BigDecimal getVolume() {
    return this.volume;
  }

  public Date getStartTime() {
    return this.startTime;
  }

  @Override
  public String toString() {
    return "FtxCandleDto{"
        + "startTime="
        + startTime.toString()
        + "open="
        + open
        + ", high="
        + high
        + ", low="
        + low
        + ", close="
        + close
        + '}';
  }
}
