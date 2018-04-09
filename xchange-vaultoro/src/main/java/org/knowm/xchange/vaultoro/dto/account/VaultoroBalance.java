package org.knowm.xchange.vaultoro.dto.account;

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
@JsonPropertyOrder({"currency_code", "cash", "reserved"})
public class VaultoroBalance {

  @JsonProperty("currency_code")
  private String currencyCode;

  @JsonProperty("cash")
  private BigDecimal cash;

  @JsonProperty("reserved")
  private BigDecimal reserved;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** @return The currencyCode */
  @JsonProperty("currency_code")
  public String getCurrencyCode() {

    return currencyCode;
  }

  /** @param currencyCode The currency_code */
  @JsonProperty("currency_code")
  public void setCurrencyCode(String currencyCode) {

    this.currencyCode = currencyCode;
  }

  /** @return The cash */
  @JsonProperty("cash")
  public BigDecimal getCash() {

    return cash;
  }

  /** @param cash The cash */
  @JsonProperty("cash")
  public void setCash(BigDecimal cash) {

    this.cash = cash;
  }

  /** @return The reserved */
  @JsonProperty("reserved")
  public BigDecimal getReserved() {

    return reserved;
  }

  /** @param reserved The reserved */
  @JsonProperty("reserved")
  public void setReserved(BigDecimal reserved) {

    this.reserved = reserved;
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
