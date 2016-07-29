package org.knowm.xchange.btcchina.dto.account;

import java.util.Arrays;

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

    return "BTCChinaDepositObject [deposits=" + Arrays.toString(deposits) + "]";
  }

}
