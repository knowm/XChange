package org.knowm.xchange.bitmex.dto.trade;

import java.math.BigDecimal;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** See {@link org.knowm.xchange.bitmex.BitmexAuthenticated#replaceOrder} */
@SuppressWarnings({"unused", "WeakerAccess"})
public class BitmexReplaceOrderParameters {

  @Nullable private final String orderId;
  @Nullable private final String origClOrdId;
  @Nullable private final String clOrdId;
  @Nullable private final BigDecimal simpleOrderQuantity;
  @Nullable private final BigDecimal orderQuantity;
  @Nullable private final BigDecimal simpleLeavesQuantity;
  @Nullable private final BigDecimal leavesQuantity;
  @Nullable private final BigDecimal price;
  @Nullable private final BigDecimal stopPrice;
  @Nullable private final BigDecimal pegOffsetValue;
  @Nullable private final String text;

  public BitmexReplaceOrderParameters(
      @Nullable String orderId,
      @Nullable String origClOrdId,
      @Nullable String clOrdId,
      @Nullable BigDecimal simpleOrderQuantity,
      @Nullable BigDecimal orderQuantity,
      @Nullable BigDecimal simpleLeavesQuantity,
      @Nullable BigDecimal leavesQuantity,
      @Nullable BigDecimal price,
      @Nullable BigDecimal stopPrice,
      @Nullable BigDecimal pegOffsetValue,
      @Nullable String text) {
    this.orderId = orderId;
    this.origClOrdId = origClOrdId;
    this.clOrdId = clOrdId;
    this.simpleOrderQuantity = simpleOrderQuantity;
    this.orderQuantity = orderQuantity;
    this.simpleLeavesQuantity = simpleLeavesQuantity;
    this.leavesQuantity = leavesQuantity;
    this.price = price;
    this.stopPrice = stopPrice;
    this.pegOffsetValue = pegOffsetValue;
    this.text = text;
  }

  @Nullable
  public String getOrderId() {
    return orderId;
  }

  @Nullable
  public String getOrigClOrdId() {
    return origClOrdId;
  }

  @Nullable
  public String getClOrdId() {
    return clOrdId;
  }

  @Nullable
  public BigDecimal getSimpleOrderQuantity() {
    return simpleOrderQuantity;
  }

  @Nullable
  public BigDecimal getOrderQuantity() {
    return orderQuantity;
  }

  @Nullable
  public BigDecimal getSimpleLeavesQuantity() {
    return simpleLeavesQuantity;
  }

  @Nullable
  public BigDecimal getLeavesQuantity() {
    return leavesQuantity;
  }

  @Nullable
  public BigDecimal getPrice() {
    return price;
  }

  @Nullable
  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  @Nullable
  public BigDecimal getPegOffsetValue() {
    return pegOffsetValue;
  }

  @Nullable
  public String getText() {
    return text;
  }

  /** See {@link org.knowm.xchange.bitmex.BitmexAuthenticated#replaceOrder} */
  public static class Builder {

    @Nullable private String orderId;
    @Nullable private String origClOrdId;
    @Nullable private String clOrdId;
    @Nullable private BigDecimal simpleOrderQuantity;
    @Nullable private BigDecimal orderQuantity;
    @Nullable private BigDecimal simpleLeavesQuantity;
    @Nullable private BigDecimal leavesQuantity;
    @Nullable private BigDecimal price;
    @Nullable private BigDecimal stopPrice;
    @Nullable private BigDecimal pegOffsetValue;
    @Nullable private String text;

    @Nonnull
    public BitmexReplaceOrderParameters build() {
      return new BitmexReplaceOrderParameters(
          orderId,
          origClOrdId,
          clOrdId,
          simpleOrderQuantity,
          orderQuantity,
          simpleLeavesQuantity,
          leavesQuantity,
          price,
          stopPrice,
          pegOffsetValue,
          text);
    }

    @Nonnull
    public Builder setOrderId(@Nullable String orderId) {
      this.orderId = orderId;
      return this;
    }

    @Nonnull
    public Builder setOrigClOrdId(@Nullable String origClOrdId) {
      this.origClOrdId = origClOrdId;
      return this;
    }

    @Nonnull
    public Builder setClOrdId(@Nullable String clOrdId) {
      this.clOrdId = clOrdId;
      return this;
    }

    @Nonnull
    public Builder setSimpleOrderQuantity(@Nullable BigDecimal simpleOrderQuantity) {
      this.simpleOrderQuantity = simpleOrderQuantity;
      return this;
    }

    @Nonnull
    public Builder setOrderQuantity(@Nullable BigDecimal orderQuantity) {
      this.orderQuantity = orderQuantity;
      return this;
    }

    @Nonnull
    public Builder setSimpleLeavesQuantity(@Nullable BigDecimal simpleLeavesQuantity) {
      this.simpleLeavesQuantity = simpleLeavesQuantity;
      return this;
    }

    @Nonnull
    public Builder setLeavesQuantity(@Nullable BigDecimal leavesQuantity) {
      this.leavesQuantity = leavesQuantity;
      return this;
    }

    @Nonnull
    public Builder setPrice(@Nullable BigDecimal price) {
      this.price = price;
      return this;
    }

    @Nonnull
    public Builder setStopPrice(@Nullable BigDecimal stopPrice) {
      this.stopPrice = stopPrice;
      return this;
    }

    @Nonnull
    public Builder setPegOffsetValue(@Nullable BigDecimal pegOffsetValue) {
      this.pegOffsetValue = pegOffsetValue;
      return this;
    }

    @Nonnull
    public Builder setText(@Nullable String text) {
      this.text = text;
      return this;
    }
  }

  @Override
  public String toString() {
    return "BitmexReplaceOrderParameters{"
        + "orderId="
        + orderId
        + ", origClOrdId='"
        + origClOrdId
        + "', clOrdId='"
        + clOrdId
        + "', simpleOrderQuantity="
        + simpleOrderQuantity
        + ", orderQuantity="
        + orderQuantity
        + ", simpleLeavesQuantity="
        + simpleLeavesQuantity
        + ", leavesQuantity="
        + leavesQuantity
        + ", price="
        + price
        + ", stopPrice="
        + stopPrice
        + ", pegOffsetValue="
        + pegOffsetValue
        + ", text='"
        + text
        + "'}";
  }
}
