package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GateioFuturesCandlestick {

  @JsonProperty("t")
  // in seconds
  private long timestamp;
  // size volume (contract size). Only returned if contract is not prefixed
  @JsonProperty("v")
  private BigDecimal volume;

  @JsonProperty("c")
  private BigDecimal close;

  @JsonProperty("h")
  private BigDecimal high;

  @JsonProperty("l")
  private BigDecimal low;

  @JsonProperty("o")
  private BigDecimal open;
  // Trading volume (unit: Quote currency)
  @JsonProperty("sum")
  private BigDecimal quoteVolume;

}
