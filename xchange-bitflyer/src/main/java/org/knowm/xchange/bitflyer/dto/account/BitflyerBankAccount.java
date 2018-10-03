package org.knowm.xchange.bitflyer.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object representing json returned from <code>GET /v1/me/getbankaccounts</code>
 *
 * <p>Example [ { "id": 3402, "is_verified": true, "bank_name": "Wells Fargo", "branch_name":
 * "1231234123", "account_type": "Checking", "account_number": "1111111", "account_name": "Name on
 * Account" } ]
 *
 * @author bryant_harris
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitflyerBankAccount {
  @JsonProperty("type")
  Long id;

  @JsonProperty("is_verified")
  boolean isVerified;

  @JsonProperty("bank_name")
  String bankName;

  @JsonProperty("branch_name")
  String branchName;

  @JsonProperty("account_type")
  String accountType;

  @JsonProperty("account_number")
  String accountNumber;

  @JsonProperty("account_name")
  String accountName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isVerified() {
    return isVerified;
  }

  public void setVerified(boolean isVerified) {
    this.isVerified = isVerified;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getBranchName() {
    return branchName;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  @Override
  public String toString() {
    return "BitflyerBankAccount [id="
        + id
        + ", isVerified="
        + isVerified
        + ", bankName="
        + bankName
        + ", branchName="
        + branchName
        + ", accountType="
        + accountType
        + ", accountNumber="
        + accountNumber
        + ", accountName="
        + accountName
        + "]";
  }
}
