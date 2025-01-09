package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ReplaceOrderCommand {

  @Nullable
  @JsonProperty("orderID")
  public final String orderId;

  @Nullable
  @JsonProperty("origClOrdID")
  public final String origClOrdID;

  @Nullable
  @JsonProperty("clOrdID")
  public final String clOrdID;

  @Nullable
  @JsonProperty("simpleOrderQty")
  public final BigDecimal simpleOrderQty;

  @Nullable
  @JsonProperty("orderQty")
  public final BigDecimal orderQuantity;

  @Nullable
  @JsonProperty("simpleLeavesQty")
  public final BigDecimal simpleLeavesQty;

  @Nullable
  @JsonProperty("leavesQty")
  public final BigDecimal leavesQty;

  @Nullable
  @JsonProperty("price")
  public final BigDecimal price;

  @Nullable
  @JsonProperty("stopPx")
  public final BigDecimal stopPrice;

  @Nullable
  @JsonProperty("pegOffsetValue")
  public final BigDecimal pegOffsetValue;

  @Nullable
  @JsonProperty("text")
  public final String text;

  /** See {@link org.knowm.xchange.bitmex.BitmexAuthenticated#replaceOrder}. */
  public ReplaceOrderCommand(@Nonnull final BitmexReplaceOrderParameters parameters) {
    this.orderId = parameters.getOrderId();
    this.origClOrdID = parameters.getOrigClOrdId();
    this.clOrdID = parameters.getClOrdId();
    this.simpleOrderQty = parameters.getSimpleOrderQuantity();
    this.orderQuantity = parameters.getOrderQuantity();
    this.simpleLeavesQty = parameters.getSimpleLeavesQuantity();
    this.leavesQty = parameters.getLeavesQuantity();
    this.price = parameters.getPrice();
    this.stopPrice = parameters.getStopPrice();
    this.pegOffsetValue = parameters.getPegOffsetValue();
    this.text = parameters.getText();
  }

  @Override
  public String toString() {
    return "ReplaceOrderCommand{"
        + ", orderId='"
        + orderId
        + '\''
        + ", origClOrdID='"
        + origClOrdID
        + '\''
        + ", clOrdID='"
        + clOrdID
        + '\''
        + ", simpleOrderQty="
        + simpleOrderQty
        + ", orderQuantity="
        + orderQuantity
        + ", simpleLeavesQty="
        + simpleLeavesQty
        + ", leavesQty="
        + leavesQty
        + ", price="
        + price
        + ", stopPrice="
        + stopPrice
        + ", pegOffsetValue="
        + pegOffsetValue
        + ", text='"
        + text
        + '\''
        + '}';
  }
}
