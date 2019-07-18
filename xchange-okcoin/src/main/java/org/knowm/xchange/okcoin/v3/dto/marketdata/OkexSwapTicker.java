package org.knowm.xchange.okcoin.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import lombok.Data;

@Data
public class OkexSwapTicker {

  @JsonProperty("instrument_id")
  private String instrumentId;

  private BigDecimal last;

  @JsonProperty("high_24h")
  private BigDecimal high24h;

  @JsonProperty("low_24h")
  private BigDecimal low24h;

  @JsonProperty("volume_24h")
  private BigDecimal volume24h;

  @JsonProperty("best_ask")
  private BigDecimal bestAsk;

  @JsonProperty("best_bid")
  private BigDecimal bestBid;

  private String timestamp;

  public Date getTimestamp() {
    return Date.from(Instant.parse(timestamp));
  }
}
