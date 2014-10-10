package com.xeiam.xchange.vaultofsatoshi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosCurrency;

/**
 * @author Michael Lagacé
 */
public final class VosWallet {

  private VosCurrency balance;
  private VosCurrency daily_withdrawal_limit;
  private VosCurrency monthly_withdrawal_limit;

  public VosWallet(@JsonProperty("balance") VosCurrency balance, @JsonProperty("daily_withdrawal_limit") VosCurrency daily_withdrawal_limit,
      @JsonProperty("monthly_withdrawal_limit") VosCurrency monthly_withdrawal_limit) {

    this.balance = balance;
    this.daily_withdrawal_limit = daily_withdrawal_limit;
    this.monthly_withdrawal_limit = monthly_withdrawal_limit;
  }

  public VosCurrency getBalance() {

    return balance;
  }

  public VosCurrency getDaily_withdrawal_limit() {

    return daily_withdrawal_limit;
  }

  public VosCurrency getMonthly_withdrawal_limit() {

    return monthly_withdrawal_limit;
  }

  @Override
  public String toString() {

    return "VosWallet [balance=" + balance + ", daily_withdrawal_limit=" + daily_withdrawal_limit + ", monthly_withdrawal_limit=" + monthly_withdrawal_limit + "]";
  }

}
