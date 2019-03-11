package org.knowm.xchange.coinbase.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseAmount;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinbaseAccountData {

  private final CoinbaseAccount data;

  private CoinbaseAccountData(@JsonProperty("data") CoinbaseAccount data) {
    this.data = data;
  }

  public CoinbaseAccount getData() {
    return data;
  }

  @Override
  public String toString() {
    return "" + data;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CoinbaseAccount {

    private final String id;
    private final String name;
    private final CoinbaseAmount balance;

    @JsonCreator
    CoinbaseAccount(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("balance") CoinbaseAmount balance) {
      this.id = id;
      this.name = name;
      this.balance = balance;
    }

    public String getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public CoinbaseAmount getBalance() {
      return balance;
    }

    @Override
    public String toString() {
      return "CoinbaseAccount [id=" + id + ", name=" + name + ", balance=" + balance + "]";
    }
  }
}
