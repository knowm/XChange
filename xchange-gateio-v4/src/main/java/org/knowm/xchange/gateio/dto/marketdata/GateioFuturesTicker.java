package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class GateioFuturesTicker {

  @JsonProperty("contract")
  String contract;

  @JsonProperty("last")
  BigDecimal lastPrice;

  @JsonProperty("change_percentage")
  BigDecimal changePercentage24h;

  @JsonProperty("total_size")
  BigDecimal totalSize;

  @JsonProperty("volume_24h")
  BigDecimal volume24h;

  @JsonProperty("volume_24h_btc")
  BigDecimal volume24hBtc;

  @JsonProperty("volume_24h_usd")
  BigDecimal volume24hUsd;

  @JsonProperty("volume_24h_base")
  BigDecimal volume24hBase;

  @JsonProperty("volume_24h_quote")
  BigDecimal volume24hQuote;

  @JsonProperty("volume_24h_settle")
  BigDecimal volume24hSettle;

  @JsonProperty("mark_price")
  BigDecimal markPrice;

  @JsonProperty("funding_rate")
  BigDecimal fundingRate;

  @JsonProperty("funding_rate_indicative")
  BigDecimal fundingRateIndicative;

  @JsonProperty("index_price")
  BigDecimal indexPrice;

  @JsonProperty("lowest_ask")
  BigDecimal lowestAsk;

  @JsonProperty("highest_bid")
  BigDecimal highestBid;

  @JsonProperty("lowest_size")
  BigDecimal lowestAskSize;

  @JsonProperty("highest_size")
  BigDecimal highestBidSize;

  @JsonProperty("low_24h")
  BigDecimal low24h;

  @JsonProperty("high_24h")
  BigDecimal high24h;

  @JsonProperty("change_utc0")
  BigDecimal changeUtc0;

  @JsonProperty("change_utc8")
  BigDecimal changeUtc8;

  @JsonProperty("quanto_base_rate")
  BigDecimal quantoBaseRate;

}
