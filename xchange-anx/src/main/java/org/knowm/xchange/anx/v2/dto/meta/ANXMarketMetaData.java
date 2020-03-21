package org.knowm.xchange.anx.v2.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.dto.meta.FeeTier;
import org.knowm.xchange.dto.meta.InstrumentMetaData;

public class ANXMarketMetaData extends InstrumentMetaData {

  public ANXMarketMetaData(
      @JsonProperty("trading_fee") BigDecimal tradingFee,
      @JsonProperty("min_amount") BigDecimal minimumAmount,
      @JsonProperty("max_amount") BigDecimal maximumAmount,
      @JsonProperty("price_scale") int priceScale,
      @JsonProperty("fee_tiers") FeeTier[] feeTiers) {
    super(tradingFee, minimumAmount, null, priceScale, feeTiers);
  }
}
