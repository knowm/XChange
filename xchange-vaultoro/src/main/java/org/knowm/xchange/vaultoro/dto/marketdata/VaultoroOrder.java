package org.knowm.xchange.vaultoro.dto.marketdata;

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
@JsonPropertyOrder({"Gold_Price", "Gold_Amount"})
public class VaultoroOrder {

  @JsonProperty("Gold_Price")
  private BigDecimal GoldPrice;
  @JsonProperty("Gold_Amount")
  private BigDecimal GoldAmount;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The GoldPrice
   */
  @JsonProperty("Gold_Price")
  public BigDecimal getGoldPrice() {

    return GoldPrice;
  }

  /**
   * @param GoldPrice The Gold_Price
   */
  @JsonProperty("Gold_Price")
  public void setGoldPrice(BigDecimal GoldPrice) {

    this.GoldPrice = GoldPrice;
  }

  /**
   * @return The GoldAmount
   */
  @JsonProperty("Gold_Amount")
  public BigDecimal getGoldAmount() {

    return GoldAmount;
  }

  /**
   * @param GoldAmount The Gold_Amount
   */
  @JsonProperty("Gold_Amount")
  public void setGoldAmount(BigDecimal GoldAmount) {

    this.GoldAmount = GoldAmount;
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

    return "VaultoroOrder [GoldPrice=" + GoldPrice + ", GoldAmount=" + GoldAmount + ", additionalProperties=" + additionalProperties + "]";
  }

}