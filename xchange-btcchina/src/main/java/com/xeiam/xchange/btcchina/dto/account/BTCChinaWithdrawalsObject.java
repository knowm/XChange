package com.xeiam.xchange.btcchina.dto.account;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joe Zhou
 */
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

    return ToStringBuilder.reflectionToString(this);
  }

}
