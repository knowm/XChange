package org.knowm.xchange.coinbaseex.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinbaseExTrade {
  private final String timestamp;
  private final long tradeId;
  private final BigDecimal price;
  private final BigDecimal size;
  private final String side;

  public CoinbaseExTrade(@JsonProperty("time") String timestamp, @JsonProperty("trade_id") long tradeId, @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size, @JsonProperty("side") String side) {
    this.timestamp = timestamp;
    this.tradeId = tradeId;
    this.price = price;
    this.size = size;
    this.side = side;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public long getTradeId() {
    return tradeId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public String getSide() {
    return side;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CoinbaseExTrade [timestamp=");
    builder.append(timestamp);
    builder.append(", tradeId=");
    builder.append(tradeId);
    builder.append(", price=");
    builder.append(price);
    builder.append(", size=");
    builder.append(size);
    builder.append(", side=");
    builder.append(side);
    builder.append("]");
    return builder.toString();
  }
}
