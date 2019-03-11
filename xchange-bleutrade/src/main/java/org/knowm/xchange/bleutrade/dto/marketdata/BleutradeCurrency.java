package org.knowm.xchange.bleutrade.dto.marketdata;

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
@JsonPropertyOrder({"Currency", "CurrencyLong", "MinConfirmation", "TxFee", "IsActive", "CoinType"})
public class BleutradeCurrency {

  @JsonProperty("Currency")
  private String Currency;

  @JsonProperty("CurrencyLong")
  private String CurrencyLong;

  @JsonProperty("MinConfirmation")
  private Integer MinConfirmation;

  @JsonProperty("TxFee")
  private BigDecimal TxFee;

  @JsonProperty("IsActive")
  private Boolean IsActive;

  @JsonProperty("CoinType")
  private String CoinType;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** @return The Currency */
  @JsonProperty("Currency")
  public String getCurrency() {

    return Currency;
  }

  /** @param Currency The Currency */
  @JsonProperty("Currency")
  public void setCurrency(String Currency) {

    this.Currency = Currency;
  }

  /** @return The CurrencyLong */
  @JsonProperty("CurrencyLong")
  public String getCurrencyLong() {

    return CurrencyLong;
  }

  /** @param CurrencyLong The CurrencyLong */
  @JsonProperty("CurrencyLong")
  public void setCurrencyLong(String CurrencyLong) {

    this.CurrencyLong = CurrencyLong;
  }

  /** @return The MinConfirmation */
  @JsonProperty("MinConfirmation")
  public Integer getMinConfirmation() {

    return MinConfirmation;
  }

  /** @param MinConfirmation The MinConfirmation */
  @JsonProperty("MinConfirmation")
  public void setMinConfirmation(Integer MinConfirmation) {

    this.MinConfirmation = MinConfirmation;
  }

  /** @return The TxFee */
  @JsonProperty("TxFee")
  public BigDecimal getTxFee() {

    return TxFee;
  }

  /** @param TxFee The TxFee */
  @JsonProperty("TxFee")
  public void setTxFee(BigDecimal TxFee) {

    this.TxFee = TxFee;
  }

  /** @return The IsActive */
  @JsonProperty("IsActive")
  public Boolean getIsActive() {

    return IsActive;
  }

  /** @param IsActive The IsActive */
  @JsonProperty("IsActive")
  public void setIsActive(Boolean IsActive) {

    this.IsActive = IsActive;
  }

  /** @return The CoinType */
  @JsonProperty("CoinType")
  public String getCoinType() {

    return CoinType;
  }

  /** @param CoinType The CoinType */
  @JsonProperty("CoinType")
  public void setCoinType(String CoinType) {

    this.CoinType = CoinType;
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

    return "BleutradeCurrency [Currency="
        + Currency
        + ", CurrencyLong="
        + CurrencyLong
        + ", MinConfirmation="
        + MinConfirmation
        + ", TxFee="
        + TxFee
        + ", IsActive="
        + IsActive
        + ", CoinType="
        + CoinType
        + ", additionalProperties="
        + additionalProperties
        + "]";
  }
}
