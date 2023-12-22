package org.knowm.xchange.bybit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitSymbol {

  @JsonProperty("name")
  String name;

  @JsonProperty("alias")
  String alias;

  @JsonProperty("status")
  String status;

  @JsonProperty("base_currency")
  String baseCurrency;

  @JsonProperty("quote_currency")
  String quoteCurrency;

  @JsonProperty("price_scale")
  Integer priceScale;

  @JsonProperty("taker_fee")
  BigDecimal takerFee;

  @JsonProperty("maker_fee")
  BigDecimal makerFee;

  @JsonProperty("funding_interval")
  Integer fundingInterval;

  @JsonProperty("leverage_filter")
  LeverageFilter leverageFilter;

  @JsonProperty("price_filter")
  PriceFilter priceFilter;

  @JsonProperty("lot_size_filter")
  LotSizeFilter lotSizeFilter;

  @Builder
  @Jacksonized
  @Value
  public static class LeverageFilter {

    @JsonProperty("min_leverage")
    Integer minLeverage;

    @JsonProperty("max_leverage")
    Integer maxLeverage;

    @JsonProperty("leverage_step")
    BigDecimal leverageStep;
  }

  @Builder
  @Jacksonized
  @Value
  public static class PriceFilter {
    @JsonProperty("min_price")
    BigDecimal minPrice;

    @JsonProperty("max_price")
    BigDecimal maxPrice;

    @JsonProperty("tick_size")
    BigDecimal tickSize;
  }

  @Builder
  @Jacksonized
  @Value
  public static class LotSizeFilter {

    @JsonProperty("max_trading_qty")
    BigDecimal maxTradingQty;

    @JsonProperty("min_trading_qty")
    BigDecimal minTradingQty;

    @JsonProperty("qty_step")
    BigDecimal qtyStep;

    @JsonProperty("post_only_max_trading_qty")
    BigDecimal postOnlyMaxTradingQty;
  }
}
