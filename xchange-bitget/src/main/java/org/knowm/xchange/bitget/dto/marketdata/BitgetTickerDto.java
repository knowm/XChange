package org.knowm.xchange.bitget.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetTickerDto {

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("high24h")
  private BigDecimal high24h;

  @JsonProperty("open")
  private BigDecimal open24h;

  @JsonProperty("lastPr")
  private BigDecimal lastPrice;

  @JsonProperty("low24h")
  private BigDecimal low24h;

  @JsonProperty("quoteVolume")
  private BigDecimal quoteVolume24h;

  @JsonProperty("baseVolume")
  private BigDecimal assetVolume24h;

  @JsonProperty("usdtVolume")
  private BigDecimal usdtVolume24h;

  @JsonProperty("bidPr")
  private BigDecimal bestBidPrice;

  @JsonProperty("bidSz")
  private BigDecimal bestBidSize;

  @JsonProperty("askPr")
  private BigDecimal bestAskPrice;

  @JsonProperty("askSz")
  private BigDecimal bestAskSize;

  @JsonProperty("openUtc")
  private BigDecimal openUtc;

  @JsonProperty("ts")
  private Instant timestamp;

  @JsonProperty("changeUtc24h")
  private BigDecimal changeUtc24h;

  @JsonProperty("change24h")
  private BigDecimal change24h;
}
