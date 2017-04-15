package org.knowm.xchange.empoex.dto.marketdata;

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
@JsonPropertyOrder({"amount", "price", "total"})
public class EmpoExLevel {

  @JsonProperty("amount")
  private String amount;
  @JsonProperty("price")
  private String price;
  @JsonProperty("total")
  private String total;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The amount
   */
  @JsonProperty("amount")
  public String getAmount() {
    return amount;
  }

  /**
   * @param amount The amount
   */
  @JsonProperty("amount")
  public void setAmount(String amount) {
    this.amount = amount;
  }

  /**
   * @return The price
   */
  @JsonProperty("price")
  public String getPrice() {
    return price;
  }

  /**
   * @param price The price
   */
  @JsonProperty("price")
  public void setPrice(String price) {
    this.price = price;
  }

  /**
   * @return The total
   */
  @JsonProperty("total")
  public String getTotal() {
    return total;
  }

  /**
   * @param total The total
   */
  @JsonProperty("total")
  public void setTotal(String total) {
    this.total = total;
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
    return "EmpoExLevel [amount=" + amount + ", price=" + price + ", total=" + total + ", additionalProperties=" + additionalProperties + "]";
  }

}