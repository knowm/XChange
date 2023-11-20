package org.knowm.xchange.bybit.dto.marketdata.instruments.option;

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
public class BybitOptionInstrumentInfo extends BybitInstrumentInfo {

  @JsonProperty("nextPageCursor")
  String nextPageCursor;

  @JsonProperty("list")
  Object list;

  @JsonProperty("optionsType")
  BybitOptionType optionsType;

  @JsonProperty("settleCoin")
  String settleCoin;

  @JsonProperty("launchTime")
  Date launchTime;

  @JsonProperty("deliveryTime")
  Date deliveryTime;

  @JsonProperty("deliveryFeeRate")
  BigDecimal deliveryFeeRate;

  @JsonProperty("priceFilter")
  PriceFilter priceFilter;

  @JsonProperty("lotSizeFilter")
  LotSizeFilter lotSizeFilter;

  public enum BybitOptionType {
    @JsonProperty("Call")
    CALL,

    @JsonProperty("Put")
    PUT
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
  }
}
