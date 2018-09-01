package org.knowm.xchange.gdax.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class GDAXTrade {

  private final String timestamp;
  private final long tradeId;
  private final BigDecimal price;
  private final BigDecimal size;
  private final String side;
  private String maker_order_id;
  private String taker_order_id;

  /**
   * @param timestamp
   * @param tradeId
   * @param price
   * @param size
   * @param side
   */
  public GDAXTrade(
      @JsonProperty("time") String timestamp,
      @JsonProperty("trade_id") long tradeId,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("side") String side) {

    this.timestamp = timestamp;
    this.tradeId = tradeId;
    this.price = price;
    this.size = size;
    this.side = side;
  }
  public GDAXTrade(
          @JsonProperty("time") String timestamp,
          @JsonProperty("trade_id") long tradeId,
          @JsonProperty("price") BigDecimal price,
          @JsonProperty("size") BigDecimal size,
          @JsonProperty("side") String side,
          @JsonProperty("maker_order_id") String maker_order_id,
          @JsonProperty("taker_order_id") String taker_order_id)
  {

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

  public String getMaker_order_id() {
    return maker_order_id;
  }

  public String getTaker_order_id() {
    return taker_order_id;
  }


  @Override
  public String toString() {
    return "GDAXTrade [timestamp="
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
