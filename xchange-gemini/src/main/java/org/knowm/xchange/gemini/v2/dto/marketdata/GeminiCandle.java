package org.knowm.xchange.gemini.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class GeminiCandle {
  public Long time;
  public BigDecimal open, high, low, close, volume;

  @Override
  public String toString() {
    return "GeminiCandle{"
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
