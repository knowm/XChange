package org.knowm.xchange.vaultoro.dto.trade;

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
@JsonPropertyOrder({"Order_ID", "status"})
public class VaultoroCancelOrderData {

  @JsonProperty("Order_ID")
  private String OrderID;
  @JsonProperty("status")
  private String status;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The OrderID
   */
  @JsonProperty("Order_ID")
  public String getOrderID() {

    return OrderID;
  }

  /**
   * @param OrderID The Order_ID
   */
  @JsonProperty("Order_ID")
  public void setOrderID(String OrderID) {

    this.OrderID = OrderID;
  }

  /**
   * @return The status
   */
  @JsonProperty("status")
  public String getStatus() {

    return status;
  }

  /**
   * @param status The status
   */
  @JsonProperty("status")
  public void setStatus(String status) {

    this.status = status;
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
