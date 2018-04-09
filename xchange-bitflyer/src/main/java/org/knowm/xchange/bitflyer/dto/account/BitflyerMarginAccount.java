package org.knowm.xchange.bitflyer.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"currency_code", "amount"})
public class BitflyerMarginAccount {
  @JsonProperty("currency_code")
  private String currencyCode;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  public void setAdditionalProperties(Map<String, Object> additionalProperties) {
    this.additionalProperties = additionalProperties;
  }

  @Override
  public String toString() {
    return "BitflyerMarginAccount{"
        + "currencyCode='"
        + currencyCode
        + '\''
        + ", amount="
        + amount
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }
}
