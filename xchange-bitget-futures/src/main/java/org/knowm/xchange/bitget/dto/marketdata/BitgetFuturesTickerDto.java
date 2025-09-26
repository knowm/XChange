package org.knowm.xchange.bitget.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitget.config.converter.StringToInstrumentConverter;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@Jacksonized
public class BitgetFuturesTickerDto {

  @JsonProperty("symbol")
  @JsonDeserialize(converter = StringToInstrumentConverter.class)
  private Instrument instrument;

  @JsonProperty("lastPr")
  private BigDecimal lastPrice;

  @JsonProperty("bidPr")
  private BigDecimal bestBidPrice;

  @JsonProperty("bidSz")
  private BigDecimal bestBidSize;

  @JsonProperty("askPr")
  private BigDecimal bestAskPrice;

  @JsonProperty("askSz")
  private BigDecimal bestAskSize;

  @JsonProperty("high24h")
  private BigDecimal high24h;

  @JsonProperty("low24h")
  private BigDecimal low24h;

  @JsonProperty("ts")
  private Instant timestamp;

  @JsonProperty("change24h")
  private BigDecimal change24h;

  @JsonProperty("quoteVolume")
  private BigDecimal quoteVolume24h;

  @JsonProperty("baseVolume")
  private BigDecimal assetVolume24h;

  @JsonProperty("usdtVolume")
  private BigDecimal usdtVolume24h;

  @JsonProperty("openUtc")
  private BigDecimal openUtc;

  @JsonProperty("changeUtc24h")
  private BigDecimal changeUtc24h;

  @JsonProperty("indexPrice")
  private BigDecimal indexPrice;

  @JsonProperty("fundingRate")
  private BigDecimal fundingRate;

  @JsonProperty("holdingAmount")
  private BigDecimal holdingAmount;

  @JsonProperty("deliveryStartTime")
  private Instant deliveryStartTime;

  @JsonProperty("deliveryTime")
  private Instant deliveryTime;

  @JsonProperty("deliveryStatus")
  private DeliveryStatus deliveryStatus;

  @JsonProperty("open24h")
  private BigDecimal open24h;

  @JsonProperty("markPrice")
  private BigDecimal markPrice;

  public enum DeliveryStatus {
    // Newly listed currency pairs are configured
    @JsonProperty("delivery_config_period")
    DELIVERY_CONFIG_PERIOD,

    // Trading normally
    @JsonEnumDefaultValue
    @JsonProperty("delivery_normal")
    DELIVERY_NORMAL,

    // 10 minutes before delivery, opening positions are prohibited
    @JsonProperty("delivery_before")
    DELIVERY_BEFORE,

    // Delivery, opening, closing, and canceling orders are prohibited
    @JsonProperty("delivery_period")
    DELIVERY_PERIOD
  }
}
