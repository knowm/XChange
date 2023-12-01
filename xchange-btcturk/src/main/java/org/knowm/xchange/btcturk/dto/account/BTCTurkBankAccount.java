package org.knowm.xchange.btcturk.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mertguner
 */
public class BTCTurkBankAccount {

  private final String bankName;
  private final String iban;

  public BTCTurkBankAccount(
      @JsonProperty("bank_name") String bankName, @JsonProperty("iban") String iban) {
    this.bankName = bankName;
    this.iban = iban;
  }

  public String getBankName() {
    return bankName;
  }

  public String getIban() {
    return iban;
  }

  @Override
  public String toString() {
    return "BTCTurkAccountBalance [bankName=" + bankName + ", iban=" + iban + "]";
  }
}
