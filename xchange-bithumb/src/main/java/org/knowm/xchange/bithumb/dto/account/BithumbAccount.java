package org.knowm.xchange.bithumb.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BithumbAccount {

  private final long created;
  private final String accountId;
  private final BigDecimal tradeFee;
  private final BigDecimal balance;

  public BithumbAccount(
      @JsonProperty("created") long created,
      @JsonProperty("account_id") String accountId,
      @JsonProperty("trade_fee") BigDecimal tradeFee,
      @JsonProperty("balance") BigDecimal balance) {
    this.created = created;
    this.accountId = accountId;
    this.tradeFee = tradeFee;
    this.balance = balance;
  }

  public long getCreated() {
    return created;
  }

  public String getAccountId() {
    return accountId;
  }

  public BigDecimal getTradeFee() {
    return tradeFee;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  @Override
  public String toString() {
    return "BithumbAccount{"
        + "created="
        + created
        + ", accountId='"
        + accountId
        + '\''
        + ", tradeFee="
        + tradeFee
        + ", balance="
        + balance
        + '}';
  }
}
