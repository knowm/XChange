package org.knowm.xchange.btcturk.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/** @author mertguner */
public class BTCTurkWithdrawalRequestInfo {
  private final String iban;
  private final List<BTCTurkKeyValues> bankList;
  private final List<BTCTurkKeyValues> friendlyNameList;
  private final String bankName;
  private final BigDecimal amount;
  private final Boolean hasBalanceRequest;
  private final String balanceRequestId;
  private final String firstName;
  private final String lastName;

  public BTCTurkWithdrawalRequestInfo(
      @JsonProperty("iban") String iban,
      @JsonProperty("bank_list") List<BTCTurkKeyValues> bankList,
      @JsonProperty("friendly_name_list") List<BTCTurkKeyValues> friendlyNameList,
      @JsonProperty("bank_name") String bankName,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("has_balance_request") Boolean hasBalanceRequest,
      @JsonProperty("balance_request_id") String balanceRequestId,
      @JsonProperty("first_name") String firstName,
      @JsonProperty("last_name") String lastName) {
    this.iban = iban;
    this.bankList = bankList;
    this.friendlyNameList = friendlyNameList;
    this.bankName = bankName;
    this.amount = amount;
    this.hasBalanceRequest = hasBalanceRequest;
    this.balanceRequestId = balanceRequestId;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getIban() {
    return iban;
  }

  public List<BTCTurkKeyValues> getFriendlyName() {
    return bankList;
  }

  public List<BTCTurkKeyValues> getFriendlyNameList() {
    return friendlyNameList;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Boolean getHasBalanceRequest() {
    return hasBalanceRequest;
  }

  public String getBalanceRequestId() {
    return balanceRequestId;
  }

  public String getBankName() {
    return bankName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @Override
  public String toString() {
    return "BTCTurkWithdrawalRequestInfo [iban="
        + iban
        + ", bankList="
        + bankList
        + ", friendlyNameList="
        + friendlyNameList
        + ", bankName="
        + bankName
        + ", amount="
        + amount
        + ", hasBalanceRequest="
        + hasBalanceRequest
        + ", balanceRequestId="
        + balanceRequestId
        + ", firstName="
        + firstName
        + ", lastName="
        + lastName
        + "]";
  }
}
