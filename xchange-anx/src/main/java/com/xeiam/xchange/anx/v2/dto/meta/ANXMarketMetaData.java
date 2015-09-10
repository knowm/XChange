package com.xeiam.xchange.anx.v2.dto.meta;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.dto.meta.MarketMetaData;

public class ANXMarketMetaData extends MarketMetaData {
  public BigDecimal maximumAmount;

  public ANXMarketMetaData(@JsonProperty("tradingFee") BigDecimal tradingFee, @JsonProperty("minAmount") BigDecimal minimumAmount,
      @JsonProperty("maxAmount") BigDecimal maximumAmount, @JsonProperty("priceScale") int priceScale) {
    super(tradingFee, minimumAmount, priceScale);

    this.maximumAmount = maximumAmount;
  }
}
