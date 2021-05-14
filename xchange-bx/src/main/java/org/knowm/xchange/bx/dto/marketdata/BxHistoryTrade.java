package org.knowm.xchange.bx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BxHistoryTrade {

  private final BigDecimal avg;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;
  private final BigDecimal open;
  private final BigDecimal close;

  public BxHistoryTrade(
      @JsonProperty("avg") BigDecimal avg,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("close") BigDecimal close) {
    this.avg = avg;
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.open = open;
    this.close = close;
  }

  public BigDecimal getAvg() {
    return avg;
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

  public BigDecimal getOpen() {
    return open;
  }

  @Override
  public String toString() {
    return "BxHistoryTrade{"
        + "avg="
        + avg
        + ", high="
        + high
        + ", low="
        + low
        + ", volume="
        + volume
        + ", open="
        + open
        + ", close="
        + close
        + '}';
  }
}
