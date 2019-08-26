package org.knowm.xchange.lgo.dto.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.StringJoiner;

public class LgoPlaceLimitOrder extends LgoPlaceOrder {

  private final String side;
  private final String productId;
  private final BigDecimal quantity;
  private final BigDecimal price;

  public LgoPlaceLimitOrder(
      long reference,
      String side,
      String productId,
      BigDecimal quantity,
      BigDecimal price,
      Instant timestamp) {
    super(reference, timestamp);
    this.side = side;
    this.productId = productId;
    this.quantity = quantity;
    this.price = price;
  }

  public String toPayload() {
    return new StringJoiner(",")
        .add("L")
        .add(side)
        .add(productId)
        .add(quantity.toPlainString())
        .add(price == null ? "" : price.toPlainString())
        .add("gtc")
        .add(String.valueOf(getTimestamp().toEpochMilli()))
        .toString();
  }

  public String getSide() {
    return side;
  }

  public String getProductId() {
    return productId;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
