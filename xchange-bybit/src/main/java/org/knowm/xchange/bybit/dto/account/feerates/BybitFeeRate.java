package org.knowm.xchange.bybit.dto.account.feerates;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitFeeRate {

  @JsonProperty("symbol")
  String symbol;

  @JsonProperty("takerFeeRate")
  BigDecimal takerFeeRate;

  @JsonProperty("makerFeeRate")
  BigDecimal makerFeeRate;
}
