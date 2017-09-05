package org.knowm.xchange.hitbtc.v2.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcTrade {

  private final String id;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final HitbtcSide side;
  private final Date timestamp;

  public HitbtcTrade(@JsonProperty("id") String id, @JsonProperty("price") BigDecimal price, @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("side") HitbtcSide side, @JsonProperty("timestamp") Date timestamp) {
    this.id = id;
    this.price = price;
    this.quantity = quantity;
    this.side = side;
    this.timestamp = timestamp;
  }

  public String getId() {
    return id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public HitbtcSide getSide() {
    return side;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "HitbtcTrade{" +
        "id='" + id + '\'' +
        ", price=" + price +
        ", quantity=" + quantity +
        ", side=" + side +
        ", timestamp=" + timestamp +
        '}';
  }

  public enum HitbtcTradesSortField {

    SORT_BY_TRADE_ID("trade_id"),
    SORT_BY_TIMESTAMP("ts");

    private final String hitbtcTradesSortField;

    HitbtcTradesSortField(String hitbtcTradesSortField) {

      this.hitbtcTradesSortField = hitbtcTradesSortField;
    }

    @Override
    public String toString() {

      return hitbtcTradesSortField;
    }
  }

}