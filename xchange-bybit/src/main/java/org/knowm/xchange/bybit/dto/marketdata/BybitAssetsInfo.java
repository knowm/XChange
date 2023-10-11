package org.knowm.xchange.bybit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

public class BybitAssetsInfo {
  @ToString
  @Getter
  @Data
  public static class BybitSpotAsset {

    @JsonProperty("coin")
    private final String coin;
    @JsonProperty("frozen")
    private final BigDecimal frozen;
    @JsonProperty("free")
    private final BigDecimal free;
    @JsonProperty("withdraw")
    private final BigDecimal withdraw;
  }
}
