package com.xeiam.xchange.coinbase.dto.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinbase.dto.CoinbasePagedResult;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser.CoinbaseUserInfo;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount;


public class CoinbaseTransactions extends CoinbasePagedResult {

  private final CoinbaseUser currentUser;
  private final CoinbaseAmount balance;
  private final List<CoinbaseTransaction> transactions;
  
  public CoinbaseTransactions(@JsonProperty("current_user") final CoinbaseUserInfo currentUser, @JsonProperty("balance") final CoinbaseAmount balance, 
      @JsonProperty("transactions") final List<CoinbaseTransaction> transactions, @JsonProperty("total_count") final int totalCount, @JsonProperty("num_pages") final int numPages,
      @JsonProperty("current_page") final int currentPage) {
    
    super(totalCount, numPages, currentPage);
    this.currentUser = new CoinbaseUser(currentUser);
    this.balance = balance;
    this.transactions = transactions;
  }

  public CoinbaseUser getCurrentUser() {

    return currentUser;
  }

  public CoinbaseAmount getBalance() {

    return balance;
  }

  public List<CoinbaseTransaction> getTransactions() {

    return transactions;
  }

  @Override
  public String toString() {

    return "CoinbaseTransactions [currentUser=" + currentUser + ", balance=" + balance + ", transactions=" + transactions + "]";
  }
}
