package org.knowm.xchange.quoine.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoineNewMarginOrderRequest extends QuoineNewOrderRequest {
  @JsonProperty("leverage_level")
  private final int leverageLevel;

  public QuoineNewMarginOrderRequest(String orderType, String currencyPairCode, String side, BigDecimal quantity, BigDecimal price,
      int leverageLevel) {
    super(orderType, currencyPairCode, side, quantity, price);

    this.leverageLevel = leverageLevel;
  }

  public int getLeverageLevel() {
    return leverageLevel;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("QuoineNewMarginOrderRequest [leverageLevel=");
    builder.append(leverageLevel);
    builder.append(", getOrderType()=");
    builder.append(getOrderType());
    builder.append(", getProductCode()=");
    builder.append(getProductCode());
    builder.append(", getCurrencyPairCode()=");
    builder.append(getCurrencyPairCode());
    builder.append(", getSide()=");
    builder.append(getSide());
    builder.append(", getQuantity()=");
    builder.append(getQuantity());
    builder.append(", getPrice()=");
    builder.append(getPrice());
    builder.append(", toString()=");
    builder.append(super.toString());
    builder.append(", getClass()=");
    builder.append(getClass());
    builder.append(", hashCode()=");
    builder.append(hashCode());
    builder.append("]");
    return builder.toString();
  }
}
