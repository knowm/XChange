package org.knowm.xchange.bybit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitTicker {

  @JsonProperty("symbol")
  String symbol;

  @JsonProperty("bid_price")
  BigDecimal bestBidPrice;

  @JsonProperty("ask_price")
  BigDecimal bestAskPrice;

  @JsonProperty("last_price")
  BigDecimal lastPrice;

  @JsonProperty("last_tick_direction")
  String lastTickDirection;

  @JsonProperty("prev_price_24h")
  BigDecimal prevPrice24h;

  @JsonProperty("price_24h_pcnt")
  BigDecimal price24hPercentageChange;

  @JsonProperty("high_price_24h")
  BigDecimal highPrice;

  @JsonProperty("low_price_24h")
  BigDecimal lowPrice;

  @JsonProperty("prev_price_1h")
  BigDecimal prevPrice1h;

  @JsonProperty("price_1h_pcnt")
  BigDecimal price1hPercentageChange;

  @JsonProperty("mark_price")
  BigDecimal markPrice;

  @JsonProperty("index_price")
  BigDecimal indexPrice;

  @JsonProperty("open_interest")
  BigDecimal openInterest;

  @JsonProperty("open_value")
  BigDecimal openValue;

  @JsonProperty("total_turnover")
  BigDecimal totalTurnover;

  @JsonProperty("turnover_24h")
  BigDecimal turnover24h;

  @JsonProperty("total_volume")
  BigDecimal totalVolume;

  @JsonProperty("volume_24h")
  BigDecimal volume24h;

  @JsonProperty("funding_rate")
  BigDecimal fundingRate;

  @JsonProperty("predicted_funding_rate")
  BigDecimal predictedFundingRate;

  @JsonProperty("next_funding_time")
  Date nextFundingTime;

  @JsonProperty("countdown_hour")
  Integer countdownHour;

  @JsonProperty("delivery_fee_rate")
  BigDecimal deliveryFeeRate;

  @JsonProperty("predicted_delivery_price")
  BigDecimal predictedDeliveryPrice;

  @JsonProperty("delivery_time")
  Date deliveryTime;
}
