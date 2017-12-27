package org.knowm.xchange.okcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinPriceLimitResult extends OkCoinErrorResult {

  private final OkCoinPriceLimit priceLimit;

  public OkCoinPriceLimitResult(@JsonProperty("result") final boolean result, @JsonProperty("error_code") final int errorCode,
      @JsonProperty("priceLimit") final OkCoinPriceLimit priceLimit) {

    super(result, errorCode);
    this.priceLimit = priceLimit;
  }

}
