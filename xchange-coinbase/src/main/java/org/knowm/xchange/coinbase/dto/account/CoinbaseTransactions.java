package org.knowm.xchange.coinbase.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinbase.dto.CoinbasePagedResult;
import org.knowm.xchange.coinbase.dto.account.CoinbaseUser.CoinbaseUserInfo;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;

/** @author jamespedwards42 */
public class CoinbaseTransactions extends CoinbasePagedResult {

  private final CoinbaseUser currentUser;
  private final CoinbaseMoney balance;
  private final List<CoinbaseTransaction> transactions;

  private CoinbaseTransactions(
      @JsonProperty("current_user") final CoinbaseUserInfo currentUser,
      @JsonProperty("balance") final CoinbaseMoney balance,
      @JsonProperty("transactions") final List<CoinbaseTransaction> transactions,
      @JsonProperty("total_count") final int totalCount,
      @JsonProperty("num_pages") final int numPages,
      @JsonProperty("current_page") final int currentPage) {

    super(totalCount, numPages, currentPage);
    this.currentUser = new CoinbaseUser(currentUser);
    this.balance = balance;
    this.transactions = transactions;
  }

  public CoinbaseUser getCurrentUser() {

    return currentUser;
  }

  public CoinbaseMoney getBalance() {

    return balance;
  }

  public List<CoinbaseTransaction> getTransactions() {

    return transactions;
  }

  @Override
  public String toString() {

    return "CoinbaseTransactions [currentUser="
        + currentUser
        + ", balance="
        + balance
        + ", transactions="
        + transactions
        + "]";
  }
}
