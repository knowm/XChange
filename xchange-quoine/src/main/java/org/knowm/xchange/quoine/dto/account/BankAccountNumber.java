package org.knowm.xchange.quoine.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author timmolter */
public final class BankAccountNumber {

  private final String accountNumber;
  private final Integer bankId;
  private final String currency;
  private final Integer id;

  /**
   * Constructor
   *
   * @param accountNumber
   * @param bankId
   * @param currency
   * @param id
   */
  public BankAccountNumber(
      @JsonProperty("account_number") String accountNumber,
      @JsonProperty("bank_id") Integer bankId,
      @JsonProperty("currency") String currency,
      @JsonProperty("id") Integer id) {
    this.accountNumber = accountNumber;
    this.bankId = bankId;
    this.currency = currency;
    this.id = id;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public Integer getBankId() {
    return bankId;
  }

  public String getCurrency() {
    return currency;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "BankAccountNumber [accountNumber="
        + accountNumber
        + ", bankId="
        + bankId
        + ", currency="
        + currency
        + ", id="
        + id
        + "]";
  }
}
