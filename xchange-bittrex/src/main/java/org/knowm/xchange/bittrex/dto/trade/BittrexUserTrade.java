package org.knowm.xchange.bittrex.dto.trade;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
  "OrderUuid",
  "Exchange",
  "TimeStamp",
  "OrderType",
  "Limit",
  "Quantity",
  "QuantityRemaining",
  "Commission",
  "Price",
  "PricePerUnit",
  "IsConditional",
  "Condition",
  "ConditionTarget",
  "ImmediateOrCancel",
  "Closed"
})
public class BittrexUserTrade {

  @JsonProperty("OrderUuid")
  private String orderUuid;

  @JsonProperty("Exchange")
  private String exchange;

  @JsonProperty("TimeStamp")
  private String timeStamp;

  @JsonProperty("OrderType")
  private String orderType;

  @JsonProperty("Limit")
  private BigDecimal limit;

  @JsonProperty("Quantity")
  private BigDecimal quantity;

  @JsonProperty("QuantityRemaining")
  private BigDecimal quantityRemaining;

  @JsonProperty("Commission")
  private BigDecimal commission;

  @JsonProperty("Price")
  private BigDecimal price;

  @JsonProperty("PricePerUnit")
  private BigDecimal pricePerUnit;

  @JsonProperty("IsConditional")
  private Boolean isConditional;

  @JsonProperty("Condition")
  private Object condition;

  @JsonProperty("ConditionTarget")
  private Object conditionTarget;

  @JsonProperty("ImmediateOrCancel")
  private Boolean immediateOrCancel;

  @JsonProperty("Closed")
  private String closed;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

  @JsonProperty("TimeStamp")
  public String getTimeStamp() {

    return timeStamp;
  }

  @JsonProperty("TimeStamp")
  public void setTimeStamp(String timeStamp) {

    this.timeStamp = timeStamp;
  }

  @JsonProperty("Closed")
  public String getClosed() {

    return closed;
  }

  @JsonProperty("Closed")
  public void setClosed(String closed) {

    this.closed = closed;
  }

  @JsonProperty("OrderType")
  public String getOrderType() {

    return orderType;
  }

  @JsonProperty("OrderType")
  public void setOrderType(String orderType) {

    this.orderType = orderType;
  }

  @JsonProperty("Limit")
  public BigDecimal getLimit() {

    return limit;
  }

  @JsonProperty("Limit")
  public void setLimit(BigDecimal limit) {

    this.limit = limit;
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

  @JsonProperty("Commission")
  public BigDecimal getCommission() {

    return commission;
  }

  @JsonProperty("Commission")
  public void setCommission(BigDecimal commission) {

    this.commission = commission;
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

  @JsonProperty("ImmediateOrCancel")
  public Boolean getImmediateOrCancel() {

    return immediateOrCancel;
  }

  @JsonProperty("ImmediateOrCancel")
  public void setImmediateOrCancel(Boolean immediateOrCancel) {

    this.immediateOrCancel = immediateOrCancel;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {

    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {

    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {

    return "BittrexUserTrade [orderUuid="
        + orderUuid
        + ", exchange="
        + exchange
        + ", timeStamp="
        + timeStamp
        + ", orderType="
        + orderType
        + ", limit="
        + limit
        + ", quantity="
        + quantity
        + ", quantityRemaining="
        + quantityRemaining
        + ", commission="
        + commission
        + ", price="
        + price
        + ", pricePerUnit="
        + pricePerUnit
        + ", isConditional="
        + isConditional
        + ", condition="
        + condition
        + ", conditionTarget="
        + conditionTarget
        + ", immediateOrCancel="
        + immediateOrCancel
        + ", additionalProperties="
        + additionalProperties
        + "]";
  }
}
