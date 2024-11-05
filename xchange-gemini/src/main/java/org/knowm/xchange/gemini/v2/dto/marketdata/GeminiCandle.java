package org.knowm.xchange.gemini.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import lombok.Data;

/** https://docs.gemini.com/rest-api/#candles */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({"time", "open", "high", "low", "close", "volume"})
@Data
public class GeminiCandle {
  private Long time;
  private BigDecimal open, high, low, close, volume;
}
