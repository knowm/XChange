package org.knowm.xchange.bleutrade.dto.account;

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
@JsonPropertyOrder({"Currency", "Address"})
public class BleutradeDepositAddress {

  @JsonProperty("Currency")
  private String Currency;
  @JsonProperty("Address")
  private String Address;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The Currency
   */
  @JsonProperty("Currency")
  public String getCurrency() {

    return Currency;
  }

  /**
   * @param Currency The Currency
   */
  @JsonProperty("Currency")
  public void setCurrency(String Currency) {

    this.Currency = Currency;
  }

  /**
   * @return The Address
   */
  @JsonProperty("Address")
  public String getAddress() {

    return Address;
  }

  /**
   * @param Address The Address
   */
  @JsonProperty("Address")
  public void setAddress(String Address) {

    this.Address = Address;
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

    return "BleutradeDepositAddress [Currency=" + Currency + ", Address=" + Address + ", additionalProperties=" + additionalProperties + "]";
  }

}
