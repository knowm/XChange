package org.knowm.xchange.bittrex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BittrexOpenOrder {

  private final String uuid;
  private final String orderUuid;
  private final String exchange;
  private final String orderType;
  private final BigDecimal quantity;
  private final BigDecimal quantityRemaining;
  private final BigDecimal limit;
  private final BigDecimal commissionPaid;
  private final BigDecimal price;
  private final BigDecimal pricePerUnit;
  private final String opened;
  private final String closed;
  private final Boolean cancelInitiated;
  private final Boolean immediateOrCancel;
  private final Boolean isConditional;
  private final String condition;
  private final Object conditionTarget;

  public BittrexOpenOrder(
      @JsonProperty("Uuid") String uuid,
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
      @JsonProperty("CancelInitiated") Boolean cancelInitiated,
      @JsonProperty("ImmediateOrCancel") Boolean immediateOrCancel,
      @JsonProperty("IsConditional") Boolean isConditional,
      @JsonProperty("Condition") String condition,
      @JsonProperty("ConditionTarget") Object conditionTarget) {
    this.uuid = uuid;
    this.orderUuid = orderUuid;
    this.exchange = exchange;
    this.orderType = orderType;
    this.quantity = quantity;
    this.quantityRemaining = quantityRemaining;
    this.limit = limit;
    this.commissionPaid = commissionPaid;
    this.price = price;
    this.pricePerUnit = pricePerUnit;
    this.opened = opened;
    this.closed = closed;
    this.cancelInitiated = cancelInitiated;
    this.immediateOrCancel = immediateOrCancel;
    this.isConditional = isConditional;
    this.condition = condition;
    this.conditionTarget = conditionTarget;
  }

  public String getUuid() {
    return uuid;
  }

  public String getOrderUuid() {
    return orderUuid;
  }

  public String getExchange() {
    return exchange;
  }

  public String getOrderType() {
    return orderType;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getQuantityRemaining() {
    return quantityRemaining;
  }

  public BigDecimal getLimit() {
    return limit;
  }

  public BigDecimal getCommissionPaid() {
    return commissionPaid;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getPricePerUnit() {
    return pricePerUnit;
  }

  public String getOpened() {
    return opened;
  }

  public String getClosed() {
    return closed;
  }

  public Boolean getCancelInitiated() {
    return cancelInitiated;
  }

  public Boolean getImmediateOrCancel() {
    return immediateOrCancel;
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

  @Override
  public String toString() {
    return "BittrexOrder [uuid="
        + getUuid()
        + ", orderUuid="
        + getOrderUuid()
        + ", exchange="
        + getExchange()
        + ", orderType="
        + getOrderType()
        + ", quantity="
        + getQuantity()
        + ", quantityRemaining="
        + getQuantityRemaining()
        + ", limit="
        + getLimit()
        + ", commissionPaid="
        + getCommissionPaid()
        + ", price="
        + getPrice()
        + ", pricePerUnit="
        + getPricePerUnit()
        + ", opened="
        + getOpened()
        + ", closed="
        + getClosed()
        + ", cancelInitiated="
        + getCancelInitiated()
        + ", immediateOrCancel="
        + getImmediateOrCancel()
        + ", isConditional="
        + getConditional()
        + ", condition="
        + getCondition()
        + ", conditionTarget="
        + getConditionTarget()
        + "]";
  }
}
