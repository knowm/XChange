package org.knowm.xchange.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
public class InstrumentMetaData implements Serializable {

  /** Trading fee (fraction) */
  private final BigDecimal tradingFee;

  /** Trading fee tiers by volume (fraction). Sorted in ascending order by quantity */
  private final FeeTier[] feeTiers;

  /** Minimum trade amount */
  private final BigDecimal minimumAmount;

  /** Maximum trade amount */
  private final BigDecimal maximumAmount;

  /** Minimum trade amount */
  private final BigDecimal counterMinimumAmount;

  /** Maximum trade amount */
  private final BigDecimal counterMaximumAmount;

  /** Decimal places for counter amount */
  private final Integer priceScale;

  /** Decimal places for volume amount */
  private final Integer volumeScale;

  /** Amount step size. If set, any amounts must be a multiple of this */
  private final BigDecimal amountStepSize;

  /** Price step size. If set, any price must be a multiple of this */
  private final BigDecimal priceStepSize;

  /** Currency that will be used to change for this trade. */
  private final Currency tradingFeeCurrency;

  /** Is market order type allowed on this pair. */
  private final boolean marketOrderEnabled;

  private final BigDecimal contractValue;

  public InstrumentMetaData(
      @JsonProperty("trading_fee") BigDecimal tradingFee,
      @JsonProperty("fee_tiers") FeeTier[] feeTiers,
      @JsonProperty("min_amount") BigDecimal minimumAmount,
      @JsonProperty("max_amount") BigDecimal maximumAmount,
      @JsonProperty("counter_min_amount") BigDecimal counterMinimumAmount,
      @JsonProperty("counter_max_amount") BigDecimal counterMaximumAmount,
      @JsonProperty("price_scale") Integer priceScale,
      @JsonProperty("volume_scale") Integer volumeScale,
      @JsonProperty("amount_step_size") BigDecimal amountStepSize,
      @JsonProperty("price_step_size") BigDecimal priceStepSize,
      @JsonProperty("trading_fee_currency") Currency tradingFeeCurrency,
      @JsonProperty("market_order_enabled") boolean marketOrderEnabled,
      @JsonProperty("contract_value") BigDecimal contractValue) {
    this.tradingFee = tradingFee;
    if (feeTiers != null) {
      Arrays.sort(feeTiers);
    }
    this.feeTiers = feeTiers;
    this.minimumAmount = (minimumAmount != null) ? minimumAmount.stripTrailingZeros() : null;
    this.maximumAmount = (maximumAmount != null) ? maximumAmount.stripTrailingZeros() : null;
    this.counterMinimumAmount =
        (counterMinimumAmount != null) ? counterMinimumAmount.stripTrailingZeros() : null;
    this.counterMaximumAmount =
        (counterMaximumAmount != null) ? counterMaximumAmount.stripTrailingZeros() : null;
    this.priceScale = priceScale;
    this.volumeScale = volumeScale;
    this.amountStepSize = (amountStepSize != null) ? amountStepSize.stripTrailingZeros() : null;
    this.priceStepSize = (priceStepSize != null) ? priceStepSize.stripTrailingZeros() : null;
    this.tradingFeeCurrency = tradingFeeCurrency;
    this.marketOrderEnabled = marketOrderEnabled;
    this.contractValue = contractValue;
  }
}
