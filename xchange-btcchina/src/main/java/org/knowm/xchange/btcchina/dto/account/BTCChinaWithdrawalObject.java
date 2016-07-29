package org.knowm.xchange.btcchina.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaWithdrawalObject {

  private final BTCChinaWithdrawal withdrawal;

  public BTCChinaWithdrawalObject(@JsonProperty("withdrawal") BTCChinaWithdrawal withdrawal) {

    this.withdrawal = withdrawal;
  }

  public BTCChinaWithdrawal getWithdrawal() {

    return withdrawal;
  }

  @Override
  public String toString() {

    return "BTCChinaWithdrawalObject [withdrawal=" + withdrawal + "]";
  }

}
