package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class DsxTrade {

  private final String id;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final DsxSide side;
  private final Date timestamp;

  public DsxTrade(
      @JsonProperty("id") String id,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("side") DsxSide side,
      @JsonProperty("timestamp") Date timestamp) {
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

  public DsxSide getSide() {
    return side;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "DsxTrade{"
        + "id='"
        + id
        + '\''
        + ", price="
        + price
        + ", quantity="
        + quantity
        + ", side="
        + side
        + ", timestamp="
        + timestamp
        + '}';
  }

  public enum DsxTradesSortField {
    SORT_BY_TRADE_ID("trade_id"),
    SORT_BY_TIMESTAMP("ts");

    private final String dsxTradesSortField;

    DsxTradesSortField(String dsxTradesSortField) {

      this.dsxTradesSortField = dsxTradesSortField;
    }

    @Override
    public String toString() {

      return dsxTradesSortField;
    }
  }
}
