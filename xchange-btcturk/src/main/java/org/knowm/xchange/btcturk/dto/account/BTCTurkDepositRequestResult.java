package org.knowm.xchange.btcturk.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author mertguner
 */
public class BTCTurkDepositRequestResult {

  private final String id;
  private final String depositCode;
  private final List<BTCTurkBankAccount> banks;
  private final String currencyType;
  private final BigDecimal amount;
  private final String firstName;
  private final String lastName;
  private final String accountOwner;

  public BTCTurkDepositRequestResult(
      @JsonProperty("id") String id,
      @JsonProperty("deposit_code") String depositCode,
      @JsonProperty("banks") List<BTCTurkBankAccount> banks,
      @JsonProperty("currency_type") String currencyType,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("first_name") String firstName,
      @JsonProperty("last_name") String lastName,
      @JsonProperty("account_owner") String accountOwner) {
    this.id = id;
    this.depositCode = depositCode;
    this.banks = banks;
    this.currencyType = currencyType;
    this.amount = amount;
    this.firstName = firstName;
    this.lastName = lastName;
    this.accountOwner = accountOwner;
  }

  public String getId() {
    return id;
  }

  public String getDepositCode() {
    return depositCode;
  }

  public List<BTCTurkBankAccount> getBanks() {
    return banks;
  }

  public String getCurrencyType() {
    return currencyType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getAccountOwner() {
    return accountOwner;
  }

  @Override
  public String toString() {
    return "BTCTurkDepositRequestResult [id="
        + id
        + ", depositCode="
        + depositCode
        + ", banks="
        + banks
        + ", currencyType="
        + currencyType
        + ", amount="
        + amount
        + ", firstName="
        + firstName
        + ", lastName="
        + lastName
        + ", accountOwner="
        + accountOwner
        + "]";
  }
}
