package org.knowm.xchange.anx.v2.dto.account;

import org.knowm.xchange.anx.v2.dto.ANXValue;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing a Wallet from ANX
 */
public final class ANXWallet {

  private final ANXValue balance;
  private final ANXValue dailyWithdrawLimit;
  private final ANXValue maxWithdraw;
  private final ANXValue monthlyWithdrawLimit;
  private final ANXValue availableBalance;
  private final int operations;

  /**
   * Constructor
   *
   * @param balance
   * @param dailyWithdrawLimit
   * @param maxWithdraw
   * @param monthlyWithdrawLimit
   * @param availableBalance
   * @param operations
   */
  public ANXWallet(@JsonProperty("Balance") ANXValue balance, @JsonProperty("Daily_Withdrawal_Limit") ANXValue dailyWithdrawLimit,
      @JsonProperty("Max_Withdraw") ANXValue maxWithdraw, @JsonProperty("Monthly_Withdraw_Limit") ANXValue monthlyWithdrawLimit,
      @JsonProperty("Available_Balance") ANXValue availableBalance, @JsonProperty("Operations") int operations) {

    this.balance = balance;
    this.dailyWithdrawLimit = dailyWithdrawLimit;
    this.maxWithdraw = maxWithdraw;
    this.monthlyWithdrawLimit = monthlyWithdrawLimit;
    this.availableBalance = availableBalance;
    this.operations = operations;
  }

  public ANXValue getBalance() {

    return this.balance;
  }

  public ANXValue getAvailableBalance() {

    return this.availableBalance;
  }

  public ANXValue getDailyWithdrawLimit() {

    return this.dailyWithdrawLimit;
  }

  public ANXValue getMaxWithdraw() {

    return this.maxWithdraw;
  }

  public ANXValue getMonthlyWithdrawLimit() {

    return this.monthlyWithdrawLimit;
  }

  public int getOperations() {

    return this.operations;
  }

  @Override
  public String toString() {

    return "ANXWallet [balance=" + balance + ", dailyWithdrawLimit=" + dailyWithdrawLimit + ", maxWithdraw=" + maxWithdraw + ", monthlyWithdrawLimit="
        + monthlyWithdrawLimit + ", operations=" + operations + "]";
  }

}
