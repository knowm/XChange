package org.knowm.xchange.bybit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.config.converter.StringToBooleanConverter;
import org.knowm.xchange.bybit.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@Builder
@Jacksonized
public class BybitInstrumentInfo {

  @JsonProperty("symbol")
  String symbol;

  @JsonProperty("baseCoin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  Currency base;

  @JsonProperty("quoteCoin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  Currency counter;

  @JsonProperty("innovation")
  @JsonDeserialize(converter = StringToBooleanConverter.class)
  Boolean innovation;

  @JsonProperty("status")
  Status status;

  @JsonProperty("marginTrading")
  MarginTrading marginTrading;

  @JsonProperty("lotSizeFilter")
  LotSizeFilter lotSizeFilter;

  @JsonProperty("priceFilter")
  PriceFilter priceFilter;


  public CurrencyPair getCurrencyPair() {
    return new CurrencyPair(base, counter);
  }


  @Data
  @Builder
  @Jacksonized
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


  @Data
  @Builder
  @Jacksonized
  public static class PriceFilter {

    @JsonProperty("tickSize")
    BigDecimal tickSize;

  }



  @Getter
  @AllArgsConstructor
  public enum Status {
    PRE_LAUNCH("PreLaunch"),
    TRADING("Trading"),
    SETTLING("Settling"),
    DELIVERING("DELIVERING"),
    CLOSED("Closed");

    @JsonValue
    private final String value;

  }


  @Getter
  @AllArgsConstructor
  public enum MarginTrading {
    NONE("none"),
    BOTH("both"),
    UTA_ONLY("utaOnly"),
    NORMAL_SPOT_ONLY("normalSpotOnly");

    @JsonValue
    private final String value;


  }


}
