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
}
