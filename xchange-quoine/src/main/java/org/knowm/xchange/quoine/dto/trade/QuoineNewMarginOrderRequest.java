package org.knowm.xchange.quoine.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class QuoineNewMarginOrderRequest extends QuoineNewOrderRequest {
  @JsonProperty("leverage_level")
  private final int leverageLevel;

  @JsonProperty("funding_currency")
  private final String fundingCurrency;

  @JsonProperty("order_direction")
  private final String orderDirection;

  public QuoineNewMarginOrderRequest(
      String orderType,
      int productCode,
      String side,
      BigDecimal quantity,
      BigDecimal price,
      int leverageLevel,
      String fundingCurrency) {
    super(orderType, productCode, side, quantity, price);

    this.leverageLevel = leverageLevel;
    this.fundingCurrency = fundingCurrency;

    this.orderDirection = "netout";
  }

  public int getLeverageLevel() {
    return leverageLevel;
  }

  public String getFundingCurrency() {
    return fundingCurrency;
  }

  public String getOrderDirection() {
    return orderDirection;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("QuoineNewMarginOrderRequest [leverageLevel=");
    builder.append(leverageLevel);
    builder.append(", getFundingCurrency()=");
    builder.append(fundingCurrency);
    builder.append(", getOrderType()=");
    builder.append(getOrderType());
    builder.append(", getProductId()=");
    builder.append(getProductId());
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
