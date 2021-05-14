package info.bitrich.xchangestream.btcmarkets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BTCMarketsWebSocketTickerMessage {

  private final String marketId;

  private final String timestamp;

  private final String messageType;

  private final BigDecimal bestBid;
  private final BigDecimal bestAsk;
  private final BigDecimal lastPrice;
  private final BigDecimal volume24h;

  public BTCMarketsWebSocketTickerMessage(
      @JsonProperty("marketId") String marketId,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("bestBid") BigDecimal bestBid,
      @JsonProperty("bestAsk") BigDecimal bestAsk,
      @JsonProperty("lastPrice") BigDecimal lastPrice,
      @JsonProperty("volume24h") BigDecimal volume24h,
      @JsonProperty("messageType") String messageType) {
    this.marketId = marketId;
    this.timestamp = timestamp;
    this.messageType = messageType;
    this.bestBid = bestBid;
    this.bestAsk = bestAsk;
    this.lastPrice = lastPrice;
    this.volume24h = volume24h;
  }

  public String getMarketId() {
    return marketId;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getMessageType() {
    return messageType;
  }

  public BigDecimal getBestBid() {
    return bestBid;
  }

  public BigDecimal getBestAsk() {
    return bestAsk;
  }

  public BigDecimal getLastPrice() {
    return lastPrice;
  }

  public BigDecimal getVolume24h() {
    return volume24h;
  }
}
