package org.knowm.xchange.coinsuper.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
  "orderNo",
  "action",
  "orderType",
  "priceLimit",
  "symbol",
  "quantity",
  "quantityRemaining",
  "amount",
  "amountRemaining",
  "fee",
  "utcUpdate",
  "utcCreate",
  "state"
})
public class OrderList {

  @JsonProperty("orderNo")
  private long orderNo;

  @JsonProperty("action")
  private String action;

  @JsonProperty("orderType")
  private String orderType;

  @JsonProperty("priceLimit")
  private String priceLimit;

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("quantity")
  private String quantity;

  @JsonProperty("quantityRemaining")
  private String quantityRemaining;

  @JsonProperty("amount")
  private String amount;

  @JsonProperty("amountRemaining")
  private String amountRemaining;

  @JsonProperty("fee")
  private String fee;

  @JsonProperty("utcUpdate")
  private long utcUpdate;

  @JsonProperty("utcCreate")
  private long utcCreate;

  @JsonProperty("state")
  private String state;

  /** No args constructor for use in serialization */
  public OrderList() {}

  /**
   * @param priceLimit
   * @param symbol
   * @param orderType
   * @param state
   * @param amountRemaining
   * @param amount
   * @param fee
   * @param orderNo
   * @param utcCreate
   * @param utcUpdate
   * @param action
   * @param quantityRemaining
   * @param quantity
   */
  public OrderList(
      long orderNo,
      String action,
      String orderType,
      String priceLimit,
      String symbol,
      String quantity,
      String quantityRemaining,
      String amount,
      String amountRemaining,
      String fee,
      long utcUpdate,
      long utcCreate,
      String state) {
    super();
    this.orderNo = orderNo;
    this.action = action;
    this.orderType = orderType;
    this.priceLimit = priceLimit;
    this.symbol = symbol;
    this.quantity = quantity;
    this.quantityRemaining = quantityRemaining;
    this.amount = amount;
    this.amountRemaining = amountRemaining;
    this.fee = fee;
    this.utcUpdate = utcUpdate;
    this.utcCreate = utcCreate;
    this.state = state;
  }

  @JsonProperty("orderNo")
  public long getOrderNo() {
    return orderNo;
  }

  @JsonProperty("orderNo")
  public void setOrderNo(long orderNo) {
    this.orderNo = orderNo;
  }

  @JsonProperty("action")
  public String getAction() {
    return action;
  }

  @JsonProperty("action")
  public void setAction(String action) {
    this.action = action;
  }

  @JsonProperty("orderType")
  public String getOrderType() {
    return orderType;
  }

  @JsonProperty("orderType")
  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  @JsonProperty("priceLimit")
  public String getPriceLimit() {
    return priceLimit;
  }

  @JsonProperty("priceLimit")
  public void setPriceLimit(String priceLimit) {
    this.priceLimit = priceLimit;
  }

  @JsonProperty("symbol")
  public String getSymbol() {
    return symbol;
  }

  @JsonProperty("symbol")
  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  @JsonProperty("quantity")
  public String getQuantity() {
    return quantity;
  }

  @JsonProperty("quantity")
  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  @JsonProperty("quantityRemaining")
  public String getQuantityRemaining() {
    return quantityRemaining;
  }

  @JsonProperty("quantityRemaining")
  public void setQuantityRemaining(String quantityRemaining) {
    this.quantityRemaining = quantityRemaining;
  }

  @JsonProperty("amount")
  public String getAmount() {
    return amount;
  }

  @JsonProperty("amount")
  public void setAmount(String amount) {
    this.amount = amount;
  }

  @JsonProperty("amountRemaining")
  public String getAmountRemaining() {
    return amountRemaining;
  }

  @JsonProperty("amountRemaining")
  public void setAmountRemaining(String amountRemaining) {
    this.amountRemaining = amountRemaining;
  }

  @JsonProperty("fee")
  public String getFee() {
    return fee;
  }

  @JsonProperty("fee")
  public void setFee(String fee) {
    this.fee = fee;
  }

  @JsonProperty("utcUpdate")
  public long getUtcUpdate() {
    return utcUpdate;
  }

  @JsonProperty("utcUpdate")
  public void setUtcUpdate(long utcUpdate) {
    this.utcUpdate = utcUpdate;
  }

  @JsonProperty("utcCreate")
  public long getUtcCreate() {
    return utcCreate;
  }

  @JsonProperty("utcCreate")
  public void setUtcCreate(long utcCreate) {
    this.utcCreate = utcCreate;
  }

  @JsonProperty("state")
  public String getState() {
    return state;
  }

  @JsonProperty("state")
  public void setState(String state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("orderNo", orderNo)
        .append("action", action)
        .append("orderType", orderType)
        .append("priceLimit", priceLimit)
        .append("symbol", symbol)
        .append("quantity", quantity)
        .append("quantityRemaining", quantityRemaining)
        .append("amount", amount)
        .append("amountRemaining", amountRemaining)
        .append("fee", fee)
        .append("utcUpdate", utcUpdate)
        .append("utcCreate", utcCreate)
        .append("state", state)
        .toString();
  }
}
