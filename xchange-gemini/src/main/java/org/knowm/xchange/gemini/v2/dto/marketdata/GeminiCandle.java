package org.knowm.xchange.gemini.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

/** https://docs.gemini.com/rest-api/#candles */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Data
public class GeminiCandle {
  private Long time;
  private BigDecimal open, high, low, close, volume;

  public GeminiCandle(
      @JsonProperty("symbol") Long time,
      @JsonProperty("symbol") BigDecimal open,
      @JsonProperty("symbol") BigDecimal high,
      @JsonProperty("symbol") BigDecimal low,
      @JsonProperty("symbol") BigDecimal close,
      @JsonProperty("symbol") BigDecimal volume) {

    this.time = time;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
  }

  public Long getTime() {
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
}
