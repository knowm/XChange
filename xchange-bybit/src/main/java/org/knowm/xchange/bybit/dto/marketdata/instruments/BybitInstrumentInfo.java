package org.knowm.xchange.bybit.dto.marketdata.instruments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString
@Data
public abstract class BybitInstrumentInfo {

  @JsonProperty("symbol")
  String symbol;

  @JsonProperty("baseCoin")
  String baseCoin;

  @JsonProperty("quoteCoin")
  String quoteCoin;

  @JsonProperty("status")
  InstrumentStatus status;

  public enum InstrumentStatus {
    @JsonProperty("PreLaunch")
    PRE_LAUNCH,
    @JsonProperty("Trading")
    TRADING,
    @JsonProperty("Settling")
    SETTLING,
    @JsonProperty("Delivering")
    DELIVERING,
    @JsonProperty("Closed")
    CLOSED
  }
}
