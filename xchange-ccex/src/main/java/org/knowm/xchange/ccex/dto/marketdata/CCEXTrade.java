package org.knowm.xchange.ccex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CCEXTrade {

  private final String id;
  private final String timestamp;
  private final BigDecimal quantity;
  private final BigDecimal price;
  private final BigDecimal total;
  private final String fillType;
  private final String orderType;

  public CCEXTrade(
      @JsonProperty("Id") String id,
      @JsonProperty("TimeStamp") String timestamp,
      @JsonProperty("Quantity") BigDecimal quantity,
      @JsonProperty("Price") BigDecimal price,
      @JsonProperty("Total") BigDecimal total,
      @JsonProperty("FillType") String fillType,
      @JsonProperty("OrderType") String orderType) {
    super();
    this.id = id;
    this.timestamp = timestamp;
    this.quantity = quantity;
    this.price = price;
    this.total = total;
    this.fillType = fillType;
    this.orderType = orderType;
  }

  public String getId() {
    return id;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public String getFillType() {
    return fillType;
  }

  public String getOrderType() {
    return orderType;
  }
}
