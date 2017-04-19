package org.knowm.xchange.bittrex.v1.dto.trade;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"Uuid", "OrderUuid", "Exchange", "OrderType", "Quantity", "QuantityRemaining", "Limit", "CommissionPaid", "Price", "PricePerUnit", "Opened", "Closed", "CancelInitiated", "ImmediateOrCancel", "IsConditional", "Condition", "ConditionTarget"})
public class BittrexOpenOrder {

  @JsonProperty("Uuid")
  private Object uuid;
  @JsonProperty("OrderUuid")
  private String orderUuid;
  @JsonProperty("Exchange")
  private String exchange;
  @JsonProperty("OrderType")
  private String orderType;
  @JsonProperty("Quantity")
  private BigDecimal quantity;
  @JsonProperty("QuantityRemaining")
  private BigDecimal quantityRemaining;
  @JsonProperty("Limit")
  private BigDecimal limit;
  @JsonProperty("CommissionPaid")
  private BigDecimal commissionPaid;
  @JsonProperty("Price")
  private BigDecimal price;
  @JsonProperty("PricePerUnit")
  private BigDecimal pricePerUnit;
  @JsonProperty("Opened")
  private String opened;
  @JsonProperty("Closed")
  private Object closed;
  @JsonProperty("CancelInitiated")
  private Boolean cancelInitiated;
  @JsonProperty("ImmediateOrCancel")
  private Boolean immediateOrCancel;
  @JsonProperty("IsConditional")
  private Boolean isConditional;
  @JsonProperty("Condition")
  private Object condition;
  @JsonProperty("ConditionTarget")
  private Object conditionTarget;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("Uuid")
  public Object getUuid() {

    return uuid;
  }

  @JsonProperty("Uuid")
  public void setUuid(Object uuid) {

    this.uuid = uuid;
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

  @JsonProperty("OrderType")
  public String getOrderType() {

    return orderType;
  }

  @JsonProperty("OrderType")
  public void setOrderType(String orderType) {

    this.orderType = orderType;
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
  public String getOpened() {

    return opened;
  }

  @JsonProperty("Opened")
  public void setOpened(String opened) {

    this.opened = opened;
  }

  @JsonProperty("Closed")
  public Object getClosed() {

    return closed;
  }

  @JsonProperty("Closed")
  public void setClosed(Object closed) {

    this.closed = closed;
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
  public Boolean getIsConditional() {

    return isConditional;
  }

  @JsonProperty("IsConditional")
  public void setIsConditional(Boolean isConditional) {

    this.isConditional = isConditional;
  }

  @JsonProperty("Condition")
  public Object getCondition() {

    return condition;
  }

  @JsonProperty("Condition")
  public void setCondition(Object condition) {

    this.condition = condition;
  }

  @JsonProperty("ConditionTarget")
  public Object getConditionTarget() {

    return conditionTarget;
  }

  @JsonProperty("ConditionTarget")
  public void setConditionTarget(Object conditionTarget) {

    this.conditionTarget = conditionTarget;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {

    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {

    this.additionalProperties.put(name, value);
  }

}