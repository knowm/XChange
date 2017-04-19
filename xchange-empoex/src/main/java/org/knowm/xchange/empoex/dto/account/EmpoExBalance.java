package org.knowm.xchange.empoex.dto.account;

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
@JsonPropertyOrder({"Coin", "Amount"})
public class EmpoExBalance {

  @JsonProperty("Coin")
  private String Coin;
  @JsonProperty("Amount")
  private String Amount;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The Coin
   */
  @JsonProperty("Coin")
  public String getCoin() {
    return Coin;
  }

  /**
   * @param Coin The Coin
   */
  @JsonProperty("Coin")
  public void setCoin(String Coin) {
    this.Coin = Coin;
  }

  /**
   * @return The Amount
   */
  @JsonProperty("Amount")
  public String getAmount() {
    return Amount;
  }

  /**
   * @param Amount The Amount
   */
  @JsonProperty("Amount")
  public void setAmount(String Amount) {
    this.Amount = Amount;
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
    return "EmpoExBalance [Coin=" + Coin + ", Amount=" + Amount + ", additionalProperties=" + additionalProperties + "]";
  }

}