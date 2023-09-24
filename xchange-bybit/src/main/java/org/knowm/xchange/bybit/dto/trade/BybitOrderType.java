package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BybitOrderType {
  MARKET("Market"),

  LIMIT("Limit"),

  UNKNOWN("UNKNOWN"),

  STOP_LOSS("StopLoss"),

  PARTIAL_TAKE_PROFIT("PartialTakeProfit"),

  PARTIAL_STOP_LOSS("PartialStopLoss"),

  TPSL_ORDER("tpslOrder"),

  MM_RATE_CLOSE("MmRateClose"),

  STOP("Stop"),

  TAKE_PROFIT("TakeProfit");

  @JsonValue private final String value;
}
