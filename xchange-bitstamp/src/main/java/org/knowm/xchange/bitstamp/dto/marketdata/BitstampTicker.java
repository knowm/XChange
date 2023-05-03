package org.knowm.xchange.bitstamp.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BitstampTicker {

  @JsonProperty("pair")
  String pair;

  @JsonProperty("open")
  BigDecimal open;

  @JsonProperty("last")
  BigDecimal last;

  @JsonProperty("high")
  BigDecimal high;

  @JsonProperty("low")
  BigDecimal low;

  @JsonProperty("vwap")
  BigDecimal vwap;

  @JsonProperty("volume")
  BigDecimal volume;

  @JsonProperty("bid")
  BigDecimal bid;

  @JsonProperty("ask")
  BigDecimal ask;

  @JsonProperty("timestamp")
  long timestamp;

}
