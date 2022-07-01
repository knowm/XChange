package org.knowm.xchange.okcoin.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import lombok.Data;

@Data
public class OkexSpotTicker {

  @JsonProperty("best_ask")
  private BigDecimal bestAsk;

  @JsonProperty("best_bid")
  private BigDecimal bestBid;

  @JsonProperty("instrument_id")
  private String instrumentId;

  @JsonProperty("product_id")
  private String productId;

  private BigDecimal last;
  private BigDecimal ask;
  private BigDecimal bid;

  @JsonProperty("open_24h")
  private BigDecimal open24h;

  @JsonProperty("high_24h")
  private BigDecimal high24h;

  @JsonProperty("low_24h")
  private BigDecimal low24h;

  @JsonProperty("base_volume_24h")
  private BigDecimal baseVolume24h;

  private String timestamp;

  @JsonProperty("quote_volume_24h")
  private BigDecimal quoteVolume24h;

  public Date getTimestamp() {
    return Date.from(Instant.parse(timestamp));
  }
}
