package org.knowm.xchange.bleutrade.dto.account;

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
@JsonPropertyOrder({"Currency", "Balance", "Available", "Pending", "CryptoAddress", "IsActive"})
public class BleutradeBalance {

  @JsonProperty("Currency")
  private String Currency;
  @JsonProperty("Balance")
  private BigDecimal Balance;
  @JsonProperty("Available")
  private BigDecimal Available;
  @JsonProperty("Pending")
  private BigDecimal Pending;
  @JsonProperty("CryptoAddress")
  private String CryptoAddress;
  @JsonProperty("IsActive")
  private Boolean IsActive;
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
   * @return The Balance
   */
  @JsonProperty("Balance")
  public BigDecimal getBalance() {

    return Balance;
  }

  /**
   * @param Balance The Balance
   */
  @JsonProperty("Balance")
  public void setBalance(BigDecimal Balance) {

    this.Balance = Balance;
  }

  /**
   * @return The Available
   */
  @JsonProperty("Available")
  public BigDecimal getAvailable() {

    return Available;
  }

  /**
   * @param Available The Available
   */
  @JsonProperty("Available")
  public void setAvailable(BigDecimal Available) {

    this.Available = Available;
  }

  /**
   * @return The Pending
   */
  @JsonProperty("Pending")
  public BigDecimal getPending() {

    return Pending;
  }

  /**
   * @param Pending The Pending
   */
  @JsonProperty("Pending")
  public void setPending(BigDecimal Pending) {

    this.Pending = Pending;
  }

  /**
   * @return The CryptoAddress
   */
  @JsonProperty("CryptoAddress")
  public String getCryptoAddress() {

    return CryptoAddress;
  }

  /**
   * @param CryptoAddress The CryptoAddress
   */
  @JsonProperty("CryptoAddress")
  public void setCryptoAddress(String CryptoAddress) {

    this.CryptoAddress = CryptoAddress;
  }

  /**
   * @return The IsActive
   */
  @JsonProperty("IsActive")
  public Boolean getIsActive() {

    return IsActive;
  }

  /**
   * @param IsActive The IsActive
   */
  @JsonProperty("IsActive")
  public void setIsActive(Boolean IsActive) {

    this.IsActive = IsActive;
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

    return "BleutradeBalance [Currency=" + Currency + ", Balance=" + Balance + ", Available=" + Available + ", Pending=" + Pending
        + ", CryptoAddress=" + CryptoAddress + ", IsActive=" + IsActive + ", additionalProperties=" + additionalProperties + "]";
  }

}
