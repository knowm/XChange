package org.knowm.xchange.bybit.dto.marketdata.instruments.linear;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;

@SuperBuilder
@Jacksonized
@ToString(callSuper = true)
@Value
public class BybitLinearInverseInstrumentInfo extends BybitInstrumentInfo {

  @JsonProperty("contractType")
  ContractType contractType;

  @JsonProperty("launchTime")
  Date launchTime;

  @JsonProperty("deliveryTime")
  Date deliveryTime;

  @JsonProperty("deliveryFeeRate")
  BigDecimal deliveryFeeRate;

  @JsonProperty("priceScale")
  Integer priceScale;

  @JsonProperty("leverageFilter")
  LeverageFilter leverageFilter;

  @JsonProperty("priceFilter")
  PriceFilter priceFilter;

  @JsonProperty("lotSizeFilter")
  LotSizeFilter lotSizeFilter;

  @JsonProperty("unifiedMarginTrade")
  boolean unifiedMarginTrade;

  @JsonProperty("fundingInterval")
  Integer fundingInterval;

  @JsonProperty("settleCoin")
  String settleCoin;

  @JsonProperty("copyTrading")
  CopyTrading copyTrading;

  public enum CopyTrading {
    @JsonProperty("none")
    NONE,
    @JsonProperty("both")
    BOTH,
    @JsonProperty("utaOnly")
    UTA_ONLY,
    @JsonProperty("normalOnly")
    NORMAL_ONLY
  }

  public enum ContractType {
    @JsonProperty("InversePerpetual")
    INVERSE_PERPETUAL,
    @JsonProperty("LinearPerpetual")
    LINEAR_PERPETUAL,
    @JsonProperty("LinearFutures")
    LINEAR_FUTURES,
    @JsonProperty("InverseFutures")
    INVERSE_FUTURES
  }

  @Builder
  @Jacksonized
  @Value
  public static class LeverageFilter {

    @JsonProperty("minLeverage")
    Integer minLeverage;

    @JsonProperty("maxLeverage")
    BigDecimal maxLeverage;

    @JsonProperty("leverageStep")
    BigDecimal leverageStep;
  }

  @Builder
  @Jacksonized
  @Value
  public static class PriceFilter {
    @JsonProperty("tickSize")
    BigDecimal tickSize;

    @JsonProperty("minPrice")
    BigDecimal minPrice;

    @JsonProperty("maxPrice")
    BigDecimal maxPrice;
  }

  @Builder
  @Jacksonized
  @Value
  public static class LotSizeFilter {
    @JsonProperty("maxOrderQty")
    BigDecimal maxOrderQty;

    @JsonProperty("minOrderQty")
    BigDecimal minOrderQty;

    @JsonProperty("qtyStep")
    BigDecimal qtyStep;

    @JsonProperty("postOnlyMaxOrderQty")
    BigDecimal postOnlyMaxOrderQty;
  }
}
