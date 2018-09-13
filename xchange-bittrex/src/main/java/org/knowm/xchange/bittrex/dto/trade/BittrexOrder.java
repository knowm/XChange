package org.knowm.xchange.bittrex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class BittrexOrder {

  private final String accountId;
  private final String orderUuid;
  private final String exchange;
  private final String type;
  private final BigDecimal quantity;
  private final BigDecimal quantityRemaining;
  private final BigDecimal limit;
  private final BigDecimal reserved;
  private final BigDecimal reserveRemaining;
  private final BigDecimal commissionReserved;
  private final BigDecimal commissionReserveRemaining;
  private final BigDecimal commissionPaid;
  private final BigDecimal price;
  private final BigDecimal pricePerUnit;
  private final Date opened;
  private final Date closed;
  private final Boolean isOpen;
  private final String sentinel;
  private final Boolean cancelInitiated;
  private final Boolean immediateOrCancel;
  private final Boolean isConditional;
  private final String condition;
  private final String conditionTarget;

  public BittrexOrder(
      @JsonProperty("AccountId") String accountId,
      @JsonProperty("OrderUuid") String orderUuid,
      @JsonProperty("Exchange") String exchange,
      @JsonProperty("Type") String type,
      @JsonProperty("Quantity") BigDecimal quantity,
      @JsonProperty("QuantityRemaining") BigDecimal quantityRemaining,
      @JsonProperty("Limit") BigDecimal limit,
      @JsonProperty("Reserved") BigDecimal reserved,
      @JsonProperty("ReserveRemaining") BigDecimal reserveRemaining,
      @JsonProperty("CommissionReserved") BigDecimal commissionReserved,
      @JsonProperty("CommissionReserveRemaining") BigDecimal commissionReserveRemaining,
      @JsonProperty("CommissionPaid") BigDecimal commissionPaid,
      @JsonProperty("Price") BigDecimal price,
      @JsonProperty("PricePerUnit") BigDecimal pricePerUnit,
      @JsonProperty("Opened") Date opened,
      @JsonProperty("Closed") Date closed,
      @JsonProperty("IsOpen") Boolean isOpen,
      @JsonProperty("Sentinel") String sentinel,
      @JsonProperty("CancelInitiated") Boolean cancelInitiated,
      @JsonProperty("ImmediateOrCancel") Boolean immediateOrCancel,
      @JsonProperty("IsConditional") Boolean isConditional,
      @JsonProperty("Condition") String condition,
      @JsonProperty("ConditionTarget") String conditionTarget) {
    this.accountId = accountId;
    this.orderUuid = orderUuid;
    this.exchange = exchange;
    this.type = type;
    this.quantity = quantity;
    this.quantityRemaining = quantityRemaining;
    this.limit = limit;
    this.reserved = reserved;
    this.reserveRemaining = reserveRemaining;
    this.commissionReserved = commissionReserved;
    this.commissionReserveRemaining = commissionReserveRemaining;
    this.commissionPaid = commissionPaid;
    this.price = price;
    this.pricePerUnit = pricePerUnit;
    this.opened = opened;
    this.closed = closed;
    this.isOpen = isOpen;
    this.sentinel = sentinel;
    this.cancelInitiated = cancelInitiated;
    this.immediateOrCancel = immediateOrCancel;
    this.isConditional = isConditional;
    this.condition = condition;
    this.conditionTarget = conditionTarget;
  }

  public String getAccountId() {
    return accountId;
  }

  public String getOrderUuid() {
    return orderUuid;
  }

  public String getExchange() {
    return exchange;
  }

  public String getType() {
    return type;
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

  public BigDecimal getReserved() {
    return reserved;
  }

  public BigDecimal getReserveRemaining() {
    return reserveRemaining;
  }

  public BigDecimal getCommissionReserved() {
    return commissionReserved;
  }

  public BigDecimal getCommissionReserveRemaining() {
    return commissionReserveRemaining;
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

  public Date getOpened() {
    return opened;
  }

  public Date getClosed() {
    return closed;
  }

  public Boolean getOpen() {
    return isOpen;
  }

  public String getSentinel() {
    return sentinel;
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

  public String getConditionTarget() {
    return conditionTarget;
  }
}
