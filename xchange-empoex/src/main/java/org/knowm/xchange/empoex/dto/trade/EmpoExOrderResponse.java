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
@JsonPropertyOrder({"success", "message", "order_id"})
public class EmpoExOrderResponse {

  @JsonProperty("success")
  private Boolean success;
  @JsonProperty("message")
  private String message;
  @JsonProperty("order_id")
  private String orderId;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The success
   */
  @JsonProperty("success")
  public Boolean getSuccess() {
    return success;
  }

  /**
   * @param success The success
   */
  @JsonProperty("success")
  public void setSuccess(Boolean success) {
    this.success = success;
  }

  /**
   * @return The message
   */
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  /**
   * @param message The message
   */
  @JsonProperty("message")
  public void setMessage(String message) {
    this.message = message;
  }

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
    return "EmpoExOrderResponse [success=" + success + ", message=" + message + ", orderId=" + orderId + ", additionalProperties="
        + additionalProperties + "]";
  }

}