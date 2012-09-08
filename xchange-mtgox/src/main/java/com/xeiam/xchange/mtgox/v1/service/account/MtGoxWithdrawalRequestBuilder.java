package com.xeiam.xchange.mtgox.v1.service.account;

import com.xeiam.xchange.service.account.WithdrawalRequest;
import com.xeiam.xchange.service.account.WithdrawalRequestBuilder;
import org.joda.money.BigMoney;

/**
 * <p>
 * Builder to provide the following to {@link MtGoxWithdrawalService}:</p>
 * <ul>
 * <li>HTTP+HTML request/response builder</li>
 * </ul>
 * <p>
 * MtGox provides a withdrawal API only through its website interface, not via the v1 REST API. Consequently, this
 * builder handles the process of gathering the necessary information to conduct the series of requests.
 * </p>
 */

public class MtGoxWithdrawalRequestBuilder implements WithdrawalRequestBuilder {

  // Store specification state here
  private String username;
  private String password;
  private String yubiKey;
  private String bankName;
  private String sortCode;
  private String accountNumber;
  private String iban;
  private String bic;
  private BigMoney money;

  // Prevent repeat builds
  private boolean isBuilt = false;

  public static MtGoxWithdrawalRequestBuilder newWithdrawalRequest() {
    return new MtGoxWithdrawalRequestBuilder();
  }


  public MtGoxWithdrawalRequest build() {
    validateState();

    // WithdrawalRequest is a DTO and so requires a default constructor
    MtGoxWithdrawalRequest withdrawalrequest = new MtGoxWithdrawalRequest();

    withdrawalrequest.setUsername(username);
    withdrawalrequest.setPassword(password);
    withdrawalrequest.setAccountNumber(accountNumber);
    withdrawalrequest.setYubiKey(yubiKey);
    withdrawalrequest.setBankName(bankName);
    withdrawalrequest.setSortCode(sortCode);
    withdrawalrequest.setAccountNumber(accountNumber);
    withdrawalrequest.setIban(iban);
    withdrawalrequest.setBic(bic);
    withdrawalrequest.setMoney(money);

    isBuilt = true;

    return withdrawalrequest;
  }

  private void validateState() {
    if (isBuilt) {
      throw new IllegalStateException("The entity has been built");
    }
  }

  public MtGoxWithdrawalRequestBuilder withUsername(String username) {
    this.username = username;
    return this;
  }

  public MtGoxWithdrawalRequestBuilder withPassword(String password) {
    this.password = password;
    return this;
  }

  public MtGoxWithdrawalRequestBuilder withYubiKey(String yubiKey) {
    this.yubiKey = yubiKey;
    return this;
  }

  public MtGoxWithdrawalRequestBuilder withBankName(String bankName) {
    this.bankName = bankName;
    return this;
  }

  public MtGoxWithdrawalRequestBuilder withSortCode(String sortCode) {
    this.sortCode = sortCode;
    return this;
  }

  public MtGoxWithdrawalRequestBuilder withAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
    return this;
  }

  public MtGoxWithdrawalRequestBuilder withIBAN(String iban) {
    this.iban = iban;
    return this;
  }

  public MtGoxWithdrawalRequestBuilder withBIC(String bic) {
    this.bic = bic;
    return this;
  }

  public MtGoxWithdrawalRequestBuilder withMoney(BigMoney money) {
    this.money = money;
    return this;
  }
}
