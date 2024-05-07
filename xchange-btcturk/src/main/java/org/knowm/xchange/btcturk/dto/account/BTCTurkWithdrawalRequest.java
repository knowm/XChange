package org.knowm.xchange.btcturk.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mertguner
 */
public class BTCTurkWithdrawalRequest {

  private final String iban;
  private final String friendlyName;
  private final Boolean friendlyNameSave;
  private final String amount;
  private final String amountPrecision;
  private final Boolean hasBalanceRequest;
  private final String balanceRequestId;
  private final String bankId;
  private final String bankName;
  private final String firstName;
  private final String lastName;

  public BTCTurkWithdrawalRequest(
      @JsonProperty("iban") String iban,
      @JsonProperty("friendly_name") String friendlyName,
      @JsonProperty("friendly_name_save") Boolean friendlyNameSave,
      @JsonProperty("amount") String amount,
      @JsonProperty("amount_precision") String amountPrecision,
      @JsonProperty("has_balance_request") Boolean hasBalanceRequest,
      @JsonProperty("balance_request_id") String balanceRequestId,
      @JsonProperty("bank_id") String bankId,
      @JsonProperty("bank_name") String bankName,
      @JsonProperty("first_name") String firstName,
      @JsonProperty("last_name") String lastName) {
    this.iban = iban;
    this.friendlyName = friendlyName;
    this.friendlyNameSave = friendlyNameSave;
    this.amount = amount;
    this.amountPrecision = amountPrecision;
    this.hasBalanceRequest = hasBalanceRequest;
    this.balanceRequestId = balanceRequestId;
    this.bankId = bankId;
    this.bankName = bankName;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getIban() {
    return iban;
  }

  public String getFriendlyName() {
    return friendlyName;
  }

  public Boolean getFriendlyNameSave() {
    return friendlyNameSave;
  }

  public String getAmount() {
    return amount;
  }

  public String getAmountPrecision() {
    return amountPrecision;
  }

  public Boolean getHasBalanceRequest() {
    return hasBalanceRequest;
  }

  public String getBalanceRequestId() {
    return balanceRequestId;
  }

  public String getBankId() {
    return bankId;
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
    return "BTCTurkWithdrawalRequest [iban="
        + iban
        + ", friendlyName="
        + friendlyName
        + ", friendlyNameSave="
        + friendlyNameSave
        + ", amount="
        + amount
        + ", amountPrecision="
        + amountPrecision
        + ", hasBalanceRequest="
        + hasBalanceRequest
        + ", balanceRequestId="
        + balanceRequestId
        + ", bankId="
        + bankId
        + ", bankName="
        + bankName
        + ", firstName="
        + firstName
        + ", lastName="
        + lastName
        + "]";
  }
}
