package org.knowm.xchange.coinmate.dto.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.meta.FeeTier;
import org.knowm.xchange.dto.meta.InstrumentMetaData;

import java.math.BigDecimal;

public class CoinmateInstrumentMetaData extends InstrumentMetaData {

  public CoinmateInstrumentMetaData(
      @JsonProperty("trading_fee") BigDecimal tradingFee,
      @JsonProperty("fee_tiers") FeeTier[] feeTiers,
      @JsonProperty("min_amount") BigDecimal minimumAmount,
      @JsonProperty("max_amount") BigDecimal maximumAmount,
      @JsonProperty("counter_min_amount") BigDecimal counterMinimumAmount,
      @JsonProperty("counter_max_amount") BigDecimal counterMaximumAmount,
      @JsonProperty("price_scale") Integer priceScale,
      @JsonProperty("base_scale") Integer volumeScale,
      @JsonProperty("amount_step_size") BigDecimal amountStepSize,
      @JsonProperty("price_step_size") BigDecimal priceStepSize,
      @JsonProperty("trading_fee_currency") Currency tradingFeeCurrency,
      @JsonProperty("market_order_enabled") boolean marketOrderEnabled,
      @JsonProperty("contract_value") BigDecimal contractValue) {
    super(
        tradingFee,
        feeTiers,
        minimumAmount,
        maximumAmount,
        counterMinimumAmount,
        counterMaximumAmount,
        priceScale,
        volumeScale,
        amountStepSize,
        priceStepSize,
        tradingFeeCurrency,
        marketOrderEnabled,
        contractValue);
  }
}
