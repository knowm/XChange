package org.knowm.xchange.paymium.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymiumAccountOperations {

  @JsonProperty("amount")
  protected Double amount;

  @JsonProperty("created_at")
  protected String createdAt;

  @JsonProperty("created_at_int")
  protected Integer createdAtInt;

  @JsonProperty("currency")
  protected String currency;

  @JsonProperty("is_trading_account")
  protected boolean isTradingAccount;

  @JsonProperty("name")
  protected String name;

  @JsonProperty("uuid")
  protected String uuid;

  @Override
  public String toString() {
    return "PaymiumAccountOperations [amount="
        + amount
        + ", createdAt= "
        + createdAt
        + ", currency="
        + currency
        + ", isTradingAccount= "
        + isTradingAccount
        + ", createdAt="
        + createdAt
        + ", currency= "
        + currency
        + ", isTradingAccount="
        + isTradingAccount
        + ", name= "
        + name
        + ", uuid="
        + uuid
        + "]";
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public Integer getCreatedAtInt() {
    return createdAtInt;
  }

  public void setCreatedAtInt(Integer createdAtInt) {
    this.createdAtInt = createdAtInt;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public boolean isTradingAccount() {
    return isTradingAccount;
  }

  public void setTradingAccount(boolean tradingAccount) {
    isTradingAccount = tradingAccount;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }
}
