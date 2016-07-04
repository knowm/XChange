package org.knowm.xchange.anx.v2.dto.meta;

import java.math.BigDecimal;

import org.knowm.xchange.dto.meta.CurrencyPairMetaData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ANXMarketMetaData extends CurrencyPairMetaData {

  public ANXMarketMetaData(@JsonProperty("trading_fee") BigDecimal tradingFee, @JsonProperty("min_amount") BigDecimal minimumAmount,
      @JsonProperty("max_amount") BigDecimal maximumAmount, @JsonProperty("price_scale") int priceScale) {
    super(tradingFee, minimumAmount, null, priceScale);

  }
}
