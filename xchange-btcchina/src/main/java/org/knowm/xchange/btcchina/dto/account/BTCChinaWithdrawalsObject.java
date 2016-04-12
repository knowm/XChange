package org.knowm.xchange.btcchina.dto.account;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaWithdrawalsObject {

  private final BTCChinaWithdrawal[] withdrawals;

  public BTCChinaWithdrawalsObject(@JsonProperty("withdrawal") BTCChinaWithdrawal[] withdrawals) {

    this.withdrawals = withdrawals;
  }

  public BTCChinaWithdrawal[] getWithdrawals() {

    return withdrawals;
  }

  @Override
  public String toString() {

    return "BTCChinaWithdrawalsObject [withdrawals=" + Arrays.toString(withdrawals) + "]";
  }

}
