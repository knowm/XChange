package org.knowm.xchange.coindirect.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindirectTickerData {
  public final long time;
  public final BigDecimal open;
  public final BigDecimal high;
  public final BigDecimal low;
  public final BigDecimal close;
  public final BigDecimal volume;

  public CoindirectTickerData(
      @JsonProperty("time") long time,
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("close") BigDecimal close,
      @JsonProperty("volume") BigDecimal volume) {
    this.time = time;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
  }

  @Override
  public String toString() {
    return "CoindirectTickerData{"
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
        + '}';
  }
}
