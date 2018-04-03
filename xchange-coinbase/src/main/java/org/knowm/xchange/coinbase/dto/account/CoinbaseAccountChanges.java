package org.knowm.xchange.coinbase.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinbase.dto.CoinbasePagedResult;
import org.knowm.xchange.coinbase.dto.account.CoinbaseUser.CoinbaseUserInfo;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;

/** @author jamespedwards42 */
public class CoinbaseAccountChanges extends CoinbasePagedResult {

  private final CoinbaseUser currentUser;
  private final CoinbaseMoney balance;
  private final List<CoinbaseAccountChange> accountChanges;

  private CoinbaseAccountChanges(
      @JsonProperty("current_user") final CoinbaseUserInfo currentUser,
      @JsonProperty("balance") final CoinbaseMoney balance,
      @JsonProperty("account_changes") final List<CoinbaseAccountChange> accountChanges,
      @JsonProperty("total_count") final int totalCount,
      @JsonProperty("num_pages") final int numPages,
      @JsonProperty("current_page") final int currentPage) {

    super(totalCount, numPages, currentPage);
    this.currentUser = new CoinbaseUser(currentUser);
    this.balance = balance;
    this.accountChanges = accountChanges;
  }

  public CoinbaseUser getCurrentUser() {

    return currentUser;
  }

  public CoinbaseMoney getBalance() {

    return balance;
  }

  public List<CoinbaseAccountChange> getAccountChanges() {

    return accountChanges;
  }

  @Override
  public String toString() {

    return "CoinbaseAccountChanges [currentUser="
        + currentUser
        + ", balance="
        + balance
        + ", accountChanges="
        + accountChanges
        + "]";
  }
}
