package org.knowm.xchange.coinbasepro.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinbaseProTrade {

  private final String timestamp;
  private final long tradeId;
  private final BigDecimal price;
  private final BigDecimal size;
  private final String side;
  private final String maker_order_id;
  private final String taker_order_id;

  /**
   * @param timestamp
   * @param tradeId
   * @param price
   * @param size
   * @param side
   * @param maker_order_id
   * @param taker_order_id
   */
  public CoinbaseProTrade(
      @JsonProperty("time") String timestamp,
      @JsonProperty("trade_id") long tradeId,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("side") String side,
      @JsonProperty("maker_order_id") String maker_order_id,
      @JsonProperty("taker_order_id") String taker_order_id) {

    this.timestamp = timestamp;
    this.tradeId = tradeId;
    this.price = price;
    this.size = size;
    this.side = side;
    this.maker_order_id = maker_order_id;
    this.taker_order_id = taker_order_id;
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
    return maker_order_id;
  }

  public String getTakerOrderId() {
    return taker_order_id;
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
