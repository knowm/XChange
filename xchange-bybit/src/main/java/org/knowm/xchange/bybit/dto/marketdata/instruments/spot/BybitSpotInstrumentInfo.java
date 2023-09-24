package org.knowm.xchange.bybit.dto.marketdata.instruments.spot;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
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
public class BybitSpotInstrumentInfo extends BybitInstrumentInfo {

  @JsonProperty("innovation")
  int innovation;

  @JsonProperty("marginTrading")
  MarginTrading marginTrading;

  @JsonProperty("lotSizeFilter")
  LotSizeFilter lotSizeFilter;

  @JsonProperty("priceFilter")
  PriceFilter priceFilter;

  public enum MarginTrading {
    @JsonProperty("none")
    NONE,
    @JsonProperty("both")
    BOTH,
    @JsonProperty("utaOnly")
    UTA_ONLY,
    @JsonProperty("normalSpotOnly")
    NORMAL_SPOT_ONLY
  }

  @Builder
  @Jacksonized
  @Value
  public static class PriceFilter {
    @JsonProperty("tickSize")
    BigDecimal tickSize;
  }

  @Builder
  @Jacksonized
  @Value
  public static class LotSizeFilter {

    @JsonProperty("basePrecision")
    BigDecimal basePrecision;

    @JsonProperty("quotePrecision")
    BigDecimal quotePrecision;

    @JsonProperty("minOrderQty")
    BigDecimal minOrderQty;

    @JsonProperty("maxOrderQty")
    BigDecimal maxOrderQty;

    @JsonProperty("minOrderAmt")
    BigDecimal minOrderAmt;

    @JsonProperty("maxOrderAmt")
    BigDecimal maxOrderAmt;
  }
}
