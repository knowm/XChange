package org.knowm.xchange.bittrex.dto.account;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.bittrex.BittrexUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexOrder {

  @JsonProperty("AccountId")
  private String accountId;
  @JsonProperty("OrderUuid")
  private String orderUuid;
  @JsonProperty("Exchange")
  private String exchange;
  @JsonProperty("Type")
  private String type;
  @JsonProperty("Quantity")
  private BigDecimal quantity;
  @JsonProperty("QuantityRemaining")
  private BigDecimal quantityRemaining;
  @JsonProperty("Limit")
  private BigDecimal limit;
  @JsonProperty("Reserved")
  private BigDecimal reserved;
  @JsonProperty("ReserveRemaining")
  private BigDecimal reserveRemaining;
  @JsonProperty("CommissionReserved")
  private BigDecimal commissionReserved;
  @JsonProperty("CommissionReserveRemaining")
  private BigDecimal commissionReserveRemaining;
  @JsonProperty("CommissionPaid")
  private BigDecimal commissionPaid;
  @JsonProperty("Price")
  private BigDecimal price;
  @JsonProperty("PricePerUnit")
  private BigDecimal pricePerUnit;
  @JsonProperty("Opened")
  private Date opened;
  @JsonProperty("Closed")
  private Date closed;
  @JsonProperty("IsOpen")
  private Boolean isOpen;
  @JsonProperty("Sentinel")
  private String sentinel;
  @JsonProperty("CancelInitiated")
  private Boolean cancelInitiated;
  @JsonProperty("ImmediateOrCancel")
  private Boolean immediateOrCancel;
  @JsonProperty("IsConditional")
  private Boolean isConditional;
  @JsonProperty("Condition")
  private String condition;
  @JsonProperty("ConditionTarget")
  private String conditionTarget;

  @JsonProperty("AccountId")
  public String getAccountId() {
    return accountId;
  }

  @JsonProperty("AccountId")
  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  @JsonProperty("OrderUuid")
  public String getOrderUuid() {
    return orderUuid;
  }

  @JsonProperty("OrderUuid")
  public void setOrderUuid(String orderUuid) {
    this.orderUuid = orderUuid;
  }

  @JsonProperty("Exchange")
  public String getExchange() {
    return exchange;
  }

  @JsonProperty("Exchange")
  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  @JsonProperty("Type")
  public String getType() {
    return type;
  }

  @JsonProperty("Type")
  public void setType(String type) {
    this.type = type;
  }

  @JsonProperty("Quantity")
  public BigDecimal getQuantity() {
    return quantity;
  }

  @JsonProperty("Quantity")
  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  @JsonProperty("QuantityRemaining")
  public BigDecimal getQuantityRemaining() {
    return quantityRemaining;
  }

  @JsonProperty("QuantityRemaining")
  public void setQuantityRemaining(BigDecimal quantityRemaining) {
    this.quantityRemaining = quantityRemaining;
  }

  @JsonProperty("Limit")
  public BigDecimal getLimit() {
    return limit;
  }

  @JsonProperty("Limit")
  public void setLimit(BigDecimal limit) {
    this.limit = limit;
  }

  @JsonProperty("Reserved")
  public BigDecimal getReserved() {
    return reserved;
  }

  @JsonProperty("Reserved")
  public void setReserved(BigDecimal reserved) {
    this.reserved = reserved;
  }

  @JsonProperty("ReserveRemaining")
  public BigDecimal getReserveRemaining() {
    return reserveRemaining;
  }

  @JsonProperty("ReserveRemaining")
  public void setReserveRemaining(BigDecimal reserveRemaining) {
    this.reserveRemaining = reserveRemaining;
  }

  @JsonProperty("CommissionReserved")
  public BigDecimal getCommissionReserved() {
    return commissionReserved;
  }

  @JsonProperty("CommissionReserved")
  public void setCommissionReserved(BigDecimal commissionReserved) {
    this.commissionReserved = commissionReserved;
  }

  @JsonProperty("CommissionReserveRemaining")
  public BigDecimal getCommissionReserveRemaining() {
    return commissionReserveRemaining;
  }

  @JsonProperty("CommissionReserveRemaining")
  public void setCommissionReserveRemaining(BigDecimal commissionReserveRemaining) {
    this.commissionReserveRemaining = commissionReserveRemaining;
  }

  @JsonProperty("CommissionPaid")
  public BigDecimal getCommissionPaid() {
    return commissionPaid;
  }

  @JsonProperty("CommissionPaid")
  public void setCommissionPaid(BigDecimal commissionPaid) {
    this.commissionPaid = commissionPaid;
  }

  @JsonProperty("Price")
  public BigDecimal getPrice() {
    return price;
  }

  @JsonProperty("Price")
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @JsonProperty("PricePerUnit")
  public BigDecimal getPricePerUnit() {
    return pricePerUnit;
  }

  @JsonProperty("PricePerUnit")
  public void setPricePerUnit(BigDecimal pricePerUnit) {
    this.pricePerUnit = pricePerUnit;
  }

  @JsonProperty("Opened")
  public Date getOpened() {
    return opened;
  }

  @JsonProperty("Opened")
  public void setOpened(String opened) {
    this.opened = BittrexUtils.toDate(opened);
  }

  @JsonProperty("Closed")
  public Date getClosed() {
    return closed;
  }

  @JsonProperty("Closed")
  public void setClosed(String closed) {
    this.closed = BittrexUtils.toDate(closed);
  }

  @JsonProperty("IsOpen")
  public Boolean getIsOpen() {
    return isOpen;
  }

  @JsonProperty("IsOpen")
  public void setIsOpen(Boolean open) {
    isOpen = open;
  }

  @JsonProperty("Sentinel")
  public String getSentinel() {
    return sentinel;
  }

  @JsonProperty("Sentinel")
  public void setSentinel(String sentinel) {
    this.sentinel = sentinel;
  }

  @JsonProperty("CancelInitiated")
  public Boolean getCancelInitiated() {
    return cancelInitiated;
  }

  @JsonProperty("CancelInitiated")
  public void setCancelInitiated(Boolean cancelInitiated) {
    this.cancelInitiated = cancelInitiated;
  }

  @JsonProperty("ImmediateOrCancel")
  public Boolean getImmediateOrCancel() {
    return immediateOrCancel;
  }

  @JsonProperty("ImmediateOrCancel")
  public void setImmediateOrCancel(Boolean immediateOrCancel) {
    this.immediateOrCancel = immediateOrCancel;
  }

  @JsonProperty("IsConditional")
  public Boolean getConditional() {
    return isConditional;
  }

  @JsonProperty("IsConditional")
  public void setConditional(Boolean conditional) {
    isConditional = conditional;
  }

  @JsonProperty("Condition")
  public String getCondition() {
    return condition;
  }

  @JsonProperty("Condition")
  public void setCondition(String condition) {
    this.condition = condition;
  }

  @JsonProperty("ConditionTarget")
  public String getConditionTarget() {
    return conditionTarget;
  }

  @JsonProperty("ConditionTarget")
  public void setConditionTarget(String conditionTarget) {
    this.conditionTarget = conditionTarget;
  }

  @Override
  public String toString() {
    return "BittrexOrder [accountId=" + accountId + ", orderUuid= " + orderUuid + ", exchange=" + exchange + "," +
        ", type= " + type + ", quantity=" + quantity + ", quantityRemaining= " + quantityRemaining + ", limit=" + limit +
        ", reserved= " + reserved + ", reserveRemaining=" + reserveRemaining + ", commissionReserved= " + commissionReserved + ", commissionReserveRemaining=" + commissionReserveRemaining +
        ", commissionPaid= " + commissionPaid + ", price=" + price + ", pricePerUnit= " + pricePerUnit + ", opened=" + opened +
        ", closed= " + closed + ", isOpen=" + isOpen + ", sentinel= " + sentinel + ", cancelInitiated=" + cancelInitiated +
        ", immediateOrCancel= " + immediateOrCancel + ", isConditional=" + isConditional + ", condition= " + condition + ", conditionTarget=" + conditionTarget + "]";
  }
}
