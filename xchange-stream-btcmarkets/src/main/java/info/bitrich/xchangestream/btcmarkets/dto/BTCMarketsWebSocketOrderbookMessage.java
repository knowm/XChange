package info.bitrich.xchangestream.btcmarkets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BTCMarketsWebSocketOrderbookMessage {
  public final String marketId;

  public final String timestamp;

  public final List<List<String>> bids;
  public final List<List<String>> asks;

  public final String messageType;

  public BTCMarketsWebSocketOrderbookMessage(
      @JsonProperty("marketId") String marketId,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("bids") List<List<String>> bids,
      @JsonProperty("asks") List<List<String>> asks,
      @JsonProperty("messageType") String messageType) {
    this.marketId = marketId;
    this.timestamp = timestamp;
    this.bids = bids;
    this.asks = asks;
    this.messageType = messageType;
  }
}
