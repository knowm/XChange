package com.xeiam.xchange.btcchina.dto.account;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaDepositObject {

  private final BTCChinaDeposit[] deposits;

  public BTCChinaDepositObject(@JsonProperty("deposit") BTCChinaDeposit[] deposits) {

    this.deposits = deposits;
  }

  public BTCChinaDeposit[] getDeposits() {

    return deposits;
  }

  @Override
  public String toString() {

    return ToStringBuilder.reflectionToString(this);
  }

}
