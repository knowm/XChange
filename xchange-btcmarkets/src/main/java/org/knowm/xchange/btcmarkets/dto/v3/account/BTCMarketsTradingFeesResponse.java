package org.knowm.xchange.btcmarkets.dto.v3.account;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCMarketsTradingFeesResponse {
  public final BigDecimal volume30Day;
  public final List<FeeByMarket> feeByMarkets;

  public BTCMarketsTradingFeesResponse(
    @JsonProperty("volume30Day") BigDecimal volume30Day,
    @JsonProperty("feeByMarkets") List<FeeByMarket> feeByMarkets
  ) {
    this.volume30Day = volume30Day;
    this.feeByMarkets = feeByMarkets;
  }

  public static class FeeByMarket {
    public final BigDecimal makerFeeRate;
    public final BigDecimal takerFeeRate;
    public final String marketId;

    public FeeByMarket(
      @JsonProperty("makerFeeRate") BigDecimal makerFeeRate,
      @JsonProperty("takerFeeRate") BigDecimal takerFeeRate,
      @JsonProperty("marketId") String marketId
    ) {
      this.makerFeeRate = makerFeeRate;
      this.takerFeeRate = takerFeeRate;
      this.marketId = marketId;
    }
  }
}
