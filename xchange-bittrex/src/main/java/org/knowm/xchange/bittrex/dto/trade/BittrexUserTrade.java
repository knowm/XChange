package org.knowm.xchange.bittrex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BittrexUserTrade {

  private final String orderUuid;
  private final String exchange;
  private final String timeStamp;
  private final String orderType;
  private final BigDecimal limit;
  private final BigDecimal quantity;
  private final BigDecimal quantityRemaining;
  private final BigDecimal commission;
  private final BigDecimal price;
  private final BigDecimal pricePerUnit;
  private final Boolean isConditional;
  private final String condition;
  private final Object conditionTarget;
  private final Boolean immediateOrCancel;
  private final String closed;

  public BittrexUserTrade(
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
      @JsonProperty("IsConditional") Boolean isConditional,
      @JsonProperty("Condition") String condition,
      @JsonProperty("ConditionTarget") Object conditionTarget,
      @JsonProperty("ImmediateOrCancel") Boolean immediateOrCancel,
      @JsonProperty("Closed") String closed) {
    this.orderUuid = orderUuid;
    this.exchange = exchange;
    this.timeStamp = timeStamp;
    this.orderType = orderType;
    this.limit = limit;
    this.quantity = quantity;
    this.quantityRemaining = quantityRemaining;
    this.commission = commission;
    this.price = price;
    this.pricePerUnit = pricePerUnit;
    this.isConditional = isConditional;
    this.condition = condition;
    this.conditionTarget = conditionTarget;
    this.immediateOrCancel = immediateOrCancel;
    this.closed = closed;
  }

  public String getOrderUuid() {
    return orderUuid;
  }

  public String getExchange() {
    return exchange;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public String getOrderType() {
    return orderType;
  }

  public BigDecimal getLimit() {
    return limit;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getQuantityRemaining() {
    return quantityRemaining;
  }

  public BigDecimal getCommission() {
    return commission;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getPricePerUnit() {
    return pricePerUnit;
  }

  public Boolean getConditional() {
    return isConditional;
  }

  public String getCondition() {
    return condition;
  }

  public Object getConditionTarget() {
    return conditionTarget;
  }

  public Boolean getImmediateOrCancel() {
    return immediateOrCancel;
  }

  public String getClosed() {
    return closed;
  }

  @Override
  public String toString() {

    return "BittrexUserTrade [orderUuid="
        + getOrderUuid()
        + ", exchange="
        + getExchange()
        + ", timeStamp="
        + getTimeStamp()
        + ", orderType="
        + getOrderType()
        + ", limit="
        + getLimit()
        + ", quantity="
        + getQuantity()
        + ", quantityRemaining="
        + getQuantityRemaining()
        + ", commission="
        + getCommission()
        + ", price="
        + getPrice()
        + ", pricePerUnit="
        + getPricePerUnit()
        + ", isConditional="
        + getConditional()
        + ", condition="
        + getCondition()
        + ", conditionTarget="
        + getConditionTarget()
        + ", immediateOrCancel="
        + getImmediateOrCancel()
        + "]";
  }
}
