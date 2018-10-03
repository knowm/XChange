package org.knowm.xchange.quoine.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

/** @author timmolter */
public final class Bank {

  private final String name;
  private final String branch;
  private final String accType;
  private final String accName;
  private final String bankAddress;
  private final String swift;
  private BankAccountNumber[] bankAccountNumbers;

  /**
   * Constructor
   *
   * @param name
   * @param branch
   * @param accType
   * @param accName
   * @param bankAddress
   * @param swift
   * @param bankAccountNumbers
   */
  public Bank(
      @JsonProperty("name") String name,
      @JsonProperty("branch") String branch,
      @JsonProperty("acc_type") String accType,
      @JsonProperty("acc_name") String accName,
      @JsonProperty("bank_address") String bankAddress,
      @JsonProperty("swift") String swift,
      @JsonProperty("bank_account_numbers") BankAccountNumber[] bankAccountNumbers) {
    this.name = name;
    this.branch = branch;
    this.accType = accType;
    this.accName = accName;
    this.bankAddress = bankAddress;
    this.swift = swift;
    this.bankAccountNumbers = bankAccountNumbers;
  }

  public BankAccountNumber[] getBankAccountNumbers() {
    return bankAccountNumbers;
  }

  public void setBankAccountNumbers(BankAccountNumber[] bankAccountNumbers) {
    this.bankAccountNumbers = bankAccountNumbers;
  }

  public String getName() {
    return name;
  }

  public String getBranch() {
    return branch;
  }

  public String getAccType() {
    return accType;
  }

  public String getAccName() {
    return accName;
  }

  public String getBankAddress() {
    return bankAddress;
  }

  public String getSwift() {
    return swift;
  }

  @Override
  public String toString() {
    return "Bank [name="
        + name
        + ", branch="
        + branch
        + ", accType="
        + accType
        + ", accName="
        + accName
        + ", bankAddress="
        + bankAddress
        + ", swift="
        + swift
        + ", bankAccountNumbers="
        + Arrays.toString(bankAccountNumbers)
        + "]";
  }
}
