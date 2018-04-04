package org.knowm.xchange.luno.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LunoAccount {
  public final String id;
  public final String name;
  public final String currency;
  public final boolean defaultAccount;

  public LunoAccount(
      @JsonProperty(value = "id", required = true) String id,
      @JsonProperty(value = "name", required = true) String name,
      @JsonProperty(value = "currency", required = true) String currency,
      @JsonProperty(value = "is_default", required = true) boolean defaultAccount) {
    this.id = id;
    this.name = name;
    this.currency = currency;
    this.defaultAccount = defaultAccount;
  }

  @Override
  public String toString() {
    return "LunoAccount [id="
        + id
        + ", name="
        + name
        + ", currency="
        + currency
        + ", defaultAccount="
        + defaultAccount
        + "]";
  }
}
