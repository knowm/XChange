package org.knowm.xchange.paymium.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymiumAccountOperations {

  @JsonProperty("Amount")
  protected Double amount;

  @JsonProperty("CreatedAt")
  protected String createdAt;

  @JsonProperty("CreatedAtInt")
  protected Integer createdAtInt;

  @JsonProperty("Currency")
  protected String currency;

  @JsonProperty("IsTradingAccount")
  protected boolean isTradingAccount;

  @JsonProperty("Name")
  protected String name;

  @JsonProperty("Uuid")
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
