package org.knowm.xchange.globitex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class GlobitexAccount implements Serializable {

  @JsonProperty("account")
  private final String account;

  @JsonProperty("main")
  private final boolean main;

  @JsonProperty("balance")
  private final List<GlobitexBalance> balance;

  public GlobitexAccount(
      @JsonProperty("account") String account,
      @JsonProperty("main") boolean main,
      @JsonProperty("balance") List<GlobitexBalance> balance) {
    this.account = account;
    this.main = main;
    this.balance = balance;
  }

  public String getAccount() {
    return account;
  }

  public boolean isMain() {
    return main;
  }

  public List<GlobitexBalance> getBalance() {
    return balance;
  }

  @Override
  public String toString() {
    return "GlobitexAccount{"
        + "account='"
        + account
        + '\''
        + ", main="
        + main
        + ", balance="
        + balance
        + '}';
  }
}
