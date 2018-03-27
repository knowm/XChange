package org.knowm.xchange.quoine.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class QuoineExecution {
  public final String id;
  public final BigDecimal quantity;
  public final BigDecimal price;
  public final String takerSide;
  public final String mySide;
  public final long createdAt;
  public final String pnl;
  public final String orderId;
  public final String target;

  public QuoineExecution(
      @JsonProperty("id") String id,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("taker_side") String takerSide,
      @JsonProperty("my_side") String mySide,
      @JsonProperty("created_at") long createdAt,
      @JsonProperty("pnl") String pnl,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("target") String target) {
    this.id = id;
    this.quantity = quantity;
    this.price = price;
    this.takerSide = takerSide;
    this.mySide = mySide;
    this.createdAt = createdAt;
    this.pnl = pnl;
    this.orderId = orderId;
    this.target = target;
  }

  @Override
  public String toString() {
    return "QuoineExecution{"
        + "id='"
        + id
        + '\''
        + ", quantity="
        + quantity
        + ", price="
        + price
        + ", takerSide='"
        + takerSide
        + '\''
        + ", mySide='"
        + mySide
        + '\''
        + ", createdAt="
        + createdAt
        + ", pnl='"
        + pnl
        + '\''
        + ", orderId='"
        + orderId
        + '\''
        + ", target='"
        + target
        + '\''
        + '}';
  }
}
