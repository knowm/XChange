package org.knowm.xchange.ccex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CCEXOrderhistory {

  private String OrderUuid;
  private String Exchange;
  private String TimeStamp;
  private String OrderType;
  private BigDecimal Limit;
  private BigDecimal Quantity;
  private BigDecimal QuantityRemaining;
  private BigDecimal Commission;
  private BigDecimal Price;
  private BigDecimal PricePerUnit;
  private boolean IsConditional;
  private String Condition;
  private String ConditionTarget;
  private boolean ImmediateOrCancel;

  public CCEXOrderhistory(
      @JsonProperty("OrderUuid") String orderUuid,
      @JsonProperty("Exchange") String exchange,
      @JsonProperty("TimeStamp") String timeStamp,
      @JsonProperty("OrderType") String orderType,
      @JsonProperty("Limit") BigDecimal limit,
      @JsonProperty("Quantity") BigDecimal quantity,
      @JsonProperty("QuantityRemaining") BigDecimal quantityRemaining,
      @JsonProperty("Commission") BigDecimal commission,
      @JsonProperty("Price") BigDecimal price,
      @JsonProperty("PricePerUnit") BigDecimal pricePerUnit,
      @JsonProperty("IsConditional") boolean isConditional,
      @JsonProperty("Condition") String condition,
      @JsonProperty("ConditionTarget") String conditionTarget,
      @JsonProperty("ImmediateOrCancel") boolean immediateOrCancel) {
    super();
    OrderUuid = orderUuid;
    Exchange = exchange;
    TimeStamp = timeStamp;
    OrderType = orderType;
    Limit = limit;
    Quantity = quantity;
    QuantityRemaining = quantityRemaining;
    Commission = commission;
    Price = price;
    PricePerUnit = pricePerUnit;
    IsConditional = isConditional;
    Condition = condition;
    ConditionTarget = conditionTarget;
    ImmediateOrCancel = immediateOrCancel;
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

  public String getTimeStamp() {
    return TimeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    TimeStamp = timeStamp;
  }

  public String getOrderType() {
    return OrderType;
  }

  public void setOrderType(String orderType) {
    OrderType = orderType;
  }

  public BigDecimal getLimit() {
    return Limit;
  }

  public void setLimit(BigDecimal limit) {
    Limit = limit;
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

  public BigDecimal getCommission() {
    return Commission;
  }

  public void setCommission(BigDecimal commission) {
    Commission = commission;
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

  public boolean isImmediateOrCancel() {
    return ImmediateOrCancel;
  }

  public void setImmediateOrCancel(boolean immediateOrCancel) {
    ImmediateOrCancel = immediateOrCancel;
  }

  @Override
  public String toString() {
    return "CCEXOrderhistory [OrderUuid="
        + OrderUuid
        + ", Exchange="
        + Exchange
        + ", TimeStamp="
        + TimeStamp
        + ", OrderType="
        + OrderType
        + ", Limit="
        + Limit
        + ", Quantity="
        + Quantity
        + ", QuantityRemaining="
        + QuantityRemaining
        + ", Commission="
        + Commission
        + ", Price="
        + Price
        + ", PricePerUnit="
        + PricePerUnit
        + ", IsConditional="
        + IsConditional
        + ", Condition="
        + Condition
        + ", ConditionTarget="
        + ConditionTarget
        + ", ImmediateOrCancel="
        + ImmediateOrCancel
        + "]";
  }
}
