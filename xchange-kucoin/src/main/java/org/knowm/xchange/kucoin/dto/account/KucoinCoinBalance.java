package org.knowm.xchange.kucoin.dto.account;

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
@JsonPropertyOrder({"coinType", "balance", "freezeBalance"})
public class KucoinCoinBalance {

  @JsonProperty("coinType")
  private String coinType;

  @JsonProperty("balance")
  private BigDecimal balance;

  @JsonProperty("freezeBalance")
  private BigDecimal freezeBalance;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public KucoinCoinBalance() {}

  /**
   * @param coinType
   * @param balance
   * @param freezeBalance
   */
  public KucoinCoinBalance(String coinType, BigDecimal balance, BigDecimal freezeBalance) {
    super();
    this.coinType = coinType;
    this.balance = balance;
    this.freezeBalance = freezeBalance;
  }

  /** @return The coinType */
  @JsonProperty("coinType")
  public String getCoinType() {
    return coinType;
  }

  /** @param coinType The coinType */
  @JsonProperty("coinType")
  public void setCoinType(String coinType) {
    this.coinType = coinType;
  }

  /** @return The balance */
  @JsonProperty("balance")
  public BigDecimal getBalance() {
    return balance;
  }

  /** @param balance The balance */
  @JsonProperty("balance")
  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  /** @return The freezeBalance */
  @JsonProperty("freezeBalance")
  public BigDecimal getFreezeBalance() {
    return freezeBalance;
  }

  /** @param freezeBalance The freezeBalance */
  @JsonProperty("freezeBalance")
  public void setFreezeBalance(BigDecimal freezeBalance) {
    this.freezeBalance = freezeBalance;
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
