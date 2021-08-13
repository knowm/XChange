package org.knowm.xchange.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

public class DerivativeMetaData implements Serializable {
  private static final long serialVersionUID = 1471672854252184985L;

  /** Trading fee (fraction) */
  @JsonProperty("trading_fee")
  private final BigDecimal tradingFee;

  /** Trading fee tiers by volume (fraction). Sorted in ascending order by quantity */
  @JsonProperty("fee_tiers")
  private final FeeTier[] feeTiers;

  /** Minimum trade amount */
  @JsonProperty("min_amount")
  private final BigDecimal minimumAmount;

  /** Maximum trade amount */
  @JsonProperty("max_amount")
  private final BigDecimal maximumAmount;

  /** Decimal places for amount */
  @JsonProperty("amount_scale")
  private final Integer amountScale;

  /** Decimal places for price */
  @JsonProperty("price_scale")
  private final Integer priceScale;

  /** Amount step size. If set, any amounts must be a multiple of this */
  @JsonProperty("amount_step_size")
  private final BigDecimal amountStepSize;

  /** Price step size. If set, any price must be a multiple of this */
  @JsonProperty("price_step_size")
  private final BigDecimal priceStepSize;

  public DerivativeMetaData(
      @JsonProperty("trading_fee") BigDecimal tradingFee,
      @JsonProperty("min_amount") BigDecimal minimumAmount,
      @JsonProperty("max_amount") BigDecimal maximumAmount,
      @JsonProperty("amount_scale") Integer amountScale,
      @JsonProperty("price_scale") Integer priceScale,
      @JsonProperty("fee_tiers") FeeTier[] feeTiers,
      @JsonProperty("amount_step_size") BigDecimal amountStepSize,
      @JsonProperty("price_step_size") BigDecimal priceStepSize) {

    this.tradingFee = tradingFee;
    this.minimumAmount = minimumAmount;
    this.maximumAmount = maximumAmount;
    this.amountScale = amountScale;
    this.priceScale = priceScale;
    if (feeTiers != null) {
      Arrays.sort(feeTiers);
    }
    this.feeTiers = feeTiers;
    this.amountStepSize = amountStepSize;
    this.priceStepSize = priceStepSize;
  }

  public BigDecimal getTradingFee() {
    return tradingFee;
  }

  public FeeTier[] getFeeTiers() {
    return feeTiers;
  }

  public BigDecimal getMinimumAmount() {
    return minimumAmount;
  }

  public BigDecimal getMaximumAmount() {
    return maximumAmount;
  }

  public Integer getAmountScale() {
    return amountScale;
  }

  public Integer getPriceScale() {
    return priceScale;
  }

  public BigDecimal getAmountStepSize() {
    return amountStepSize;
  }

  public BigDecimal getPriceStepSize() {
    return priceStepSize;
  }

  public static final class Builder {
    private BigDecimal tradingFee;
    private FeeTier[] feeTiers;
    private BigDecimal minimumAmount;
    private BigDecimal maximumAmount;
    private Integer amountScale;
    private Integer priceScale;
    private BigDecimal amountStepSize;
    private BigDecimal priceStepSize;

    public Builder() {}

    public Builder tradingFee(BigDecimal val) {
      tradingFee = val;
      return this;
    }

    public Builder feeTiers(FeeTier[] val) {
      feeTiers = val;
      return this;
    }

    public Builder minimumAmount(BigDecimal val) {
      minimumAmount = val;
      return this;
    }

    public Builder maximumAmount(BigDecimal val) {
      maximumAmount = val;
      return this;
    }

    public Builder amountScale(Integer val) {
      amountScale = val;
      return this;
    }

    public Builder priceScale(Integer val) {
      priceScale = val;
      return this;
    }

    public Builder amountStepSize(BigDecimal val) {
      amountStepSize = val;
      return this;
    }

    public Builder priceStepSize(BigDecimal val) {
      priceStepSize = val;
      return this;
    }

    public DerivativeMetaData build() {

      return new DerivativeMetaData(
          tradingFee,
          minimumAmount,
          maximumAmount,
          amountScale,
          priceScale,
          feeTiers,
          amountStepSize,
          priceStepSize);
    }
  }

  @Override
  public String toString() {
    return "DerivativeMetaData [tradingFee="
        + tradingFee
        + ", minimumAmount="
        + minimumAmount
        + ", maximumAmount="
        + maximumAmount
        + ", amountScale="
        + amountScale
        + ", priceScale="
        + priceScale
        + ", amountStepSize="
        + amountStepSize
        + ", priceStepSize="
        + priceStepSize
        + "]";
  }
}
