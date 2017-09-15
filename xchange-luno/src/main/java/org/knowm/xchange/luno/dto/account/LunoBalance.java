package org.knowm.xchange.luno.dto.account;

import java.math.BigDecimal;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LunoBalance {

  private final Balance[] balance;

  public LunoBalance(@JsonProperty(value = "balance", required = true) Balance[] balance) {
    this.balance = balance != null ? balance : new Balance[0];
  }

  @Override
  public String toString() {
    return "LunoBalance [balance(" + balance.length + ")=" + Arrays.toString(balance) + "]";
  }

  public Balance[] getBalance() {
    Balance[] copy = new Balance[balance.length];
    System.arraycopy(balance, 0, copy, 0, balance.length);
    return copy;
  }

  public static class Balance {
    public final String accountId;
    public final String asset;
    public final BigDecimal balance;
    public final BigDecimal reserved;
    public final BigDecimal unconfirmed;
    public final String name;

    public Balance(@JsonProperty(value = "account_id", required = true) String accountId, @JsonProperty(value = "asset", required = true) String asset
        , @JsonProperty(value = "balance", required = true) BigDecimal balance, @JsonProperty(value = "reserved", required = true) BigDecimal reserved
        , @JsonProperty(value = "unconfirmed", required = true) BigDecimal unconfirmed, @JsonProperty(value = "name") String name) {
      super();
      this.accountId = accountId;
      this.asset = asset;
      this.balance = balance;
      this.reserved = reserved;
      this.unconfirmed = unconfirmed;
      this.name = name;
    }

    @Override
    public String toString() {
      return "Balance [accountId=" + accountId + ", asset=" + asset + ", balance=" + balance + ", reserved=" + reserved
          + ", unconfirmed=" + unconfirmed + ", name=" + name + "]";
    }
  }
}
