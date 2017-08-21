package org.knowm.xchange.hitbtc.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.knowm.xchange.hitbtc.dto.general.HitbtcSide;

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

    return new ToStringBuilder(this)
        .append("id", id)
        .append("price", price)
        .append("quantity", quantity)
        .append("side", side)
        .append("timestamp", timestamp)
        .toString();
  }

  public enum HitbtcTradesSortField {

    SORT_BY_TRADE_ID("trade_id"), SORT_BY_TIMESTAMP("ts");

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
