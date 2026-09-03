package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({"timestamp", "quoteVolume", "close", "high", "low", "open", "volume", "completed"})
public class GateioSpotCandlestick {

  @JsonProperty("timestamp")
  // in seconds
  private long timestamp;
  //Trading volume in quote currency
  @JsonProperty("volume")
  private BigDecimal volume;

  @JsonProperty("close")
  private BigDecimal close;

  @JsonProperty("high")
  private BigDecimal high;

  @JsonProperty("low")
  private BigDecimal low;

  @JsonProperty("open")
  private BigDecimal open;
  //Trading volume in base currency
  @JsonProperty("quoteVolume")
  private BigDecimal quoteVolume;
  //Whether window is closed; true means this candlestick data segment is complete, false means not yet complete
  @JsonProperty("finished")
  private boolean completed;
}
