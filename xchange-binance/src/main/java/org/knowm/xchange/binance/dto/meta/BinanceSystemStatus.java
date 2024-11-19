package org.knowm.xchange.binance.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class BinanceSystemStatus {

  // 0: normal，1：system maintenance
  @JsonProperty String status;

  // normal or system maintenance
  @JsonProperty String msg;
}
