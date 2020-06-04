package info.bitrich.xchangestream.btcmarkets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class BTCMarketsWebSocketOrderbookMessage {
  public final String marketId;

  public final String timestamp;

  public final List<List<BigDecimal>> bids;
  public final List<List<BigDecimal>> asks;

  public final String messageType;

  public BTCMarketsWebSocketOrderbookMessage(
      @JsonProperty("marketId") String marketId,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("bids") List<List<BigDecimal>> bids,
      @JsonProperty("asks") List<List<BigDecimal>> asks,
      @JsonProperty("messageType") String messageType) {
    this.marketId = marketId;
    this.timestamp = timestamp;
    this.bids = bids;
    this.asks = asks;
    this.messageType = messageType;
  }
}
