package org.knowm.xchange.bleutrade.dto.trade;

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
@JsonPropertyOrder({"orderid"})
public class BleutradeOrderId {

  @JsonProperty("orderid")
  private String orderid;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The orderid
   */
  @JsonProperty("orderid")
  public String getOrderid() {

    return orderid;
  }

  /**
   * @param orderid The orderid
   */
  @JsonProperty("orderid")
  public void setOrderid(String orderid) {

    this.orderid = orderid;
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

    return "BleutradeOrderId [orderid=" + orderid + ", additionalProperties=" + additionalProperties + "]";
  }

}
