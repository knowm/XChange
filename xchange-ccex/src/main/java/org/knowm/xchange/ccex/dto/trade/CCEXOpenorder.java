package org.knowm.xchange.ccex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CCEXOpenorder {

  private String OrderUuid;
  private String Exchange;
  private String OrderType;
  private BigDecimal Quantity;
  private BigDecimal QuantityRemaining;
  private BigDecimal Limit;
  private BigDecimal CommissionPaid;
  private BigDecimal Price;
  private BigDecimal PricePerUnit;
  private String Opened;
  private String Closed;
  private boolean CancelInitiated;
  private boolean ImmediateOrCancel;
  private boolean IsConditional;
  private String Condition;
  private String ConditionTarget;

  public CCEXOpenorder(
      @JsonProperty("OrderUuid") String orderUuid,
      @JsonProperty("Exchange") String exchange,
      @JsonProperty("OrderType") String orderType,
      @JsonProperty("Quantity") BigDecimal quantity,
      @JsonProperty("QuantityRemaining") BigDecimal quantityRemaining,
      @JsonProperty("Limit") BigDecimal limit,
      @JsonProperty("CommissionPaid") BigDecimal commissionPaid,
      @JsonProperty("Price") BigDecimal price,
      @JsonProperty("PricePerUnit") BigDecimal pricePerUnit,
      @JsonProperty("Opened") String opened,
      @JsonProperty("Closed") String closed,
      @JsonProperty("CancelInitiated") boolean cancelInitiated,
      @JsonProperty("ImmediateOrCancel") boolean immediateOrCancel,
      @JsonProperty("IsConditional") boolean isConditional,
      @JsonProperty("Condition") String condition,
      @JsonProperty("ConditionTarget") String conditionTarget) {
    super();
    OrderUuid = orderUuid;
    Exchange = exchange;
    OrderType = orderType;
    Quantity = quantity;
    QuantityRemaining = quantityRemaining;
    Limit = limit;
    CommissionPaid = commissionPaid;
    Price = price;
    PricePerUnit = pricePerUnit;
    Opened = opened;
    Closed = closed;
    CancelInitiated = cancelInitiated;
    ImmediateOrCancel = immediateOrCancel;
    IsConditional = isConditional;
    Condition = condition;
    ConditionTarget = conditionTarget;
  }

  public String getOrderUuid() {
    return OrderUuid;
  }

  public void setOrderUuid(String orderUuid) {
    OrderUuid = orderUuid;
  }

  public String getExchange() {
    return Exchange;
  }

  public void setExchange(String exchange) {
    Exchange = exchange;
  }

  public String getOrderType() {
    return OrderType;
  }

  public void setOrderType(String orderType) {
    OrderType = orderType;
  }

  public BigDecimal getQuantity() {
    return Quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    Quantity = quantity;
  }

  public BigDecimal getQuantityRemaining() {
    return QuantityRemaining;
  }

  public void setQuantityRemaining(BigDecimal quantityRemaining) {
    QuantityRemaining = quantityRemaining;
  }

  public BigDecimal getLimit() {
    return Limit;
  }

  public void setLimit(BigDecimal limit) {
    Limit = limit;
  }

  public BigDecimal getCommissionPaid() {
    return CommissionPaid;
  }

  public void setCommissionPaid(BigDecimal commissionPaid) {
    CommissionPaid = commissionPaid;
  }

  public BigDecimal getPrice() {
    return Price;
  }

  public void setPrice(BigDecimal price) {
    Price = price;
  }

  public BigDecimal getPricePerUnit() {
    return PricePerUnit;
  }

  public void setPricePerUnit(BigDecimal pricePerUnit) {
    PricePerUnit = pricePerUnit;
  }

  public String getOpened() {
    return Opened;
  }

  public void setOpened(String opened) {
    Opened = opened;
  }

  public String getClosed() {
    return Closed;
  }

  public void setClosed(String closed) {
    Closed = closed;
  }

  public boolean isCancelInitiated() {
    return CancelInitiated;
  }

  public void setCancelInitiated(boolean cancelInitiated) {
    CancelInitiated = cancelInitiated;
  }

  public boolean isImmediateOrCancel() {
    return ImmediateOrCancel;
  }

  public void setImmediateOrCancel(boolean immediateOrCancel) {
    ImmediateOrCancel = immediateOrCancel;
  }

  public boolean isIsConditional() {
    return IsConditional;
  }

  public void setIsConditional(boolean isConditional) {
    IsConditional = isConditional;
  }

  public String getCondition() {
    return Condition;
  }

  public void setCondition(String condition) {
    Condition = condition;
  }

  public String getConditionTarget() {
    return ConditionTarget;
  }

  public void setConditionTarget(String conditionTarget) {
    ConditionTarget = conditionTarget;
  }

  @Override
  public String toString() {
    return "CCEXOpenorder [OrderUuid="
        + OrderUuid
        + ", Exchange="
        + Exchange
        + ", OrderType="
        + OrderType
        + ", Quantity="
        + Quantity
        + ", QuantityRemaining="
        + QuantityRemaining
        + ", Limit="
        + Limit
        + ", CommissionPaid="
        + CommissionPaid
        + ", Price="
        + Price
        + ", PricePerUnit="
        + PricePerUnit
        + ", Opened="
        + Opened
        + ", Closed="
        + Closed
        + ", CancelInitiated="
        + CancelInitiated
        + ", ImmediateOrCancel="
        + ImmediateOrCancel
        + ", IsConditional="
        + IsConditional
        + ", Condition="
        + Condition
        + ", ConditionTarget="
        + ConditionTarget
        + "]";
  }
}
