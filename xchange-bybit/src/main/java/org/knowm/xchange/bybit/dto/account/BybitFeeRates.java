package org.knowm.xchange.bybit.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitFeeRates {

  @JsonProperty("list")
  List<BybitFeeRate> list;

  @Builder
  @Jacksonized
  @Value
  public static class BybitFeeRate {

    @JsonProperty("symbol")
    String symbol;

    @JsonProperty("takerFeeRate")
    BigDecimal takerFeeRate;

    @JsonProperty("makerFeeRate")
    BigDecimal makerFeeRate;
  }
}
