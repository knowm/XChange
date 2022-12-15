package org.knowm.xchange.dto.meta;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.currency.Currency;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
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
        this.minimumAmount = (minimumAmount != null) ? minimumAmount.stripTrailingZeros(): null;
        this.maximumAmount = (maximumAmount != null) ? maximumAmount.stripTrailingZeros(): null;
        this.counterMinimumAmount = (counterMinimumAmount != null) ? counterMinimumAmount.stripTrailingZeros(): null;
        this.counterMaximumAmount = (counterMaximumAmount != null) ? counterMaximumAmount.stripTrailingZeros(): null;
        this.priceScale = priceScale;
        this.volumeScale = volumeScale;
        this.amountStepSize = (amountStepSize != null) ? amountStepSize.stripTrailingZeros(): null;
        this.priceStepSize = (priceStepSize != null) ? priceStepSize.stripTrailingZeros(): null;
        this.tradingFeeCurrency = tradingFeeCurrency;
        this.marketOrderEnabled = marketOrderEnabled;
        this.contractValue = contractValue;
    }



    public static class Builder{
        private BigDecimal tradingFee;
        private FeeTier[] feeTiers;
        private BigDecimal minimumAmount;
        private BigDecimal maximumAmount;
        private BigDecimal counterMinimumAmount;
        private BigDecimal counterMaximumAmount;
        private Integer priceScale;
        private Integer volumeScale;
        private BigDecimal amountStepSize;
        private BigDecimal priceStepSize;
        private Currency tradingFeeCurrency;
        private boolean marketOrderEnabled;

        private BigDecimal contractValue;

        public InstrumentMetaData.Builder tradingFee(BigDecimal tradingFee) {
            this.tradingFee = tradingFee;
            return this;
        }

        public InstrumentMetaData.Builder feeTiers(FeeTier[] feeTiers) {
            this.feeTiers = feeTiers;
            return this;
        }

        public InstrumentMetaData.Builder minimumAmount(BigDecimal minimumAmount) {
            this.minimumAmount = (minimumAmount != null) ? minimumAmount.stripTrailingZeros(): null;
            return this;
        }

        public InstrumentMetaData.Builder maximumAmount(BigDecimal maximumAmount) {
            this.maximumAmount = (maximumAmount != null) ? maximumAmount.stripTrailingZeros(): null;
            return this;
        }

        public InstrumentMetaData.Builder counterMinimumAmount(BigDecimal counterMinimumAmount) {
            this.counterMinimumAmount = (counterMinimumAmount != null) ? counterMinimumAmount.stripTrailingZeros(): null;
            return this;
        }

        public InstrumentMetaData.Builder counterMaximumAmount(BigDecimal counterMaximumAmount) {
            this.counterMaximumAmount = (counterMaximumAmount != null) ? counterMaximumAmount.stripTrailingZeros(): null;
            return this;
        }

        public InstrumentMetaData.Builder priceScale(Integer priceScale) {
            this.priceScale = priceScale;
            return this;
        }

        public InstrumentMetaData.Builder volumeScale(Integer volumeScale) {
            this.volumeScale = volumeScale;
            return this;
        }

        public InstrumentMetaData.Builder amountStepSize(BigDecimal amountStepSize) {
            this.amountStepSize = (amountStepSize != null) ? amountStepSize.stripTrailingZeros(): null;
            return this;
        }

        public InstrumentMetaData.Builder priceStepSize(BigDecimal priceStepSize) {
            this.priceStepSize = (priceStepSize != null) ? priceStepSize.stripTrailingZeros(): null;
            return this;
        }

        public InstrumentMetaData.Builder tradingFeeCurrency(Currency tradingFeeCurrency) {
            this.tradingFeeCurrency = tradingFeeCurrency;
            return this;
        }

        public InstrumentMetaData.Builder marketOrderEnabled(boolean marketOrderEnabled) {
            this.marketOrderEnabled = marketOrderEnabled;
            return this;
        }

        public InstrumentMetaData.Builder contractValue(BigDecimal contractValue) {
            this.contractValue = contractValue;
            return this;
        }

        public InstrumentMetaData build() {
            return new InstrumentMetaData(
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
}
