package info.bitrich.xchangestream.lgo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class LgoUserSnapshotData {

  private final String orderId;
  private final String orderType;
  private final BigDecimal price;
  private final String side;
  private final BigDecimal quantity;
  private final BigDecimal remainingQuantity;
  private final Date orderCreationTime;

  public LgoUserSnapshotData(
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("side") String side,
      @JsonProperty("remaining_quantity") BigDecimal remainingQuantity,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("order_type") String orderType,
      @JsonProperty("order_creation_time")
          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          Date orderCreationTime) {
    this.orderId = orderId;
    this.orderType = orderType;
    this.price = price;
    this.side = side;
    this.quantity = quantity;
    this.remainingQuantity = remainingQuantity;
    this.orderCreationTime = orderCreationTime;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getOrderType() {
    return orderType;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getSide() {
    return side;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getRemainingQuantity() {
    return remainingQuantity;
  }

  public Date getOrderCreationTime() {
    return orderCreationTime;
  }
}
