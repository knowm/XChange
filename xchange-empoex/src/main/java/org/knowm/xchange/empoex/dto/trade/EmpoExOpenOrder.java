package org.knowm.xchange.empoex.dto.trade;

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
@JsonPropertyOrder({"order_id", "amount_remaining", "original_amount", "value", "type"})
public class EmpoExOpenOrder {

  @JsonProperty("order_id")
  private String orderId;
  @JsonProperty("amount_remaining")
  private String amountRemaining;
  @JsonProperty("original_amount")
  private String originalAmount;
  @JsonProperty("value")
  private String value;
  @JsonProperty("type")
  private String type;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The orderId
   */
  @JsonProperty("order_id")
  public String getOrderId() {
    return orderId;
  }

  /**
   * @param orderId The order_id
   */
  @JsonProperty("order_id")
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  /**
   * @return The amountRemaining
   */
  @JsonProperty("amount_remaining")
  public String getAmountRemaining() {
    return amountRemaining;
  }

  /**
   * @param amountRemaining The amount_remaining
   */
  @JsonProperty("amount_remaining")
  public void setAmountRemaining(String amountRemaining) {
    this.amountRemaining = amountRemaining;
  }

  /**
   * @return The originalAmount
   */
  @JsonProperty("original_amount")
  public String getOriginalAmount() {
    return originalAmount;
  }

  /**
   * @param originalAmount The original_amount
   */
  @JsonProperty("original_amount")
  public void setOriginalAmount(String originalAmount) {
    this.originalAmount = originalAmount;
  }

  /**
   * @return The value
   */
  @JsonProperty("value")
  public String getValue() {
    return value;
  }

  /**
   * @param value The value
   */
  @JsonProperty("value")
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * @return The type
   */
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  /**
   * @param type The type
   */
  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
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
    return "EmpoExOpenOrder [orderId=" + orderId + ", amountRemaining=" + amountRemaining + ", originalAmount=" + originalAmount + ", value=" + value
        + ", type=" + type + ", additionalProperties=" + additionalProperties + "]";
  }

}