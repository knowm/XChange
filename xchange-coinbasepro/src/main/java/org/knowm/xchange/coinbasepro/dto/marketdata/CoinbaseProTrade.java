package org.knowm.xchange.coinbasepro.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinbaseProTrade {

  private final String timestamp;
  private final long tradeId;
  private final BigDecimal price;
  private final BigDecimal size;
  private final String side;
  private String makerOrderId;
  private String takerOrderId;

  public CoinbaseProTrade(
      @JsonProperty("time") String timestamp,
      @JsonProperty("trade_id") long tradeId,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("side") String side,
      @JsonProperty("makerOrderId") String makerOrderId,
      @JsonProperty("takerOrderId") String takerOrderId) {

    this.timestamp = timestamp;
    this.tradeId = tradeId;
    this.price = price;
    this.size = size;
    this.side = side;
    this.makerOrderId = makerOrderId;
    this.takerOrderId = takerOrderId;
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

  public String getMakerOrderId() {
    return makerOrderId;
  }

  public String getTakerOrderId() {
    return takerOrderId;
  }

  @Override
  public String toString() {
    return "CoinbaseProTrade [timestamp="
        + timestamp
        + ", tradeId="
        + tradeId
        + ", price="
        + price
        + ", size="
        + size
        + ", side="
        + side
        + "]";
  }
}
