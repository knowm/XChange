package com.xeiam.xchange.mtgox.v1.service.account;

import org.joda.money.BigMoney;

import com.xeiam.xchange.dto.account.WithdrawalRequest;

/**
 * <p>
 * DTO to provide the following to {@link MtGoxPollingAccountService}:
 * </p>
 * <ul>
 * <li>Implements the request for the MtGox exchange</li>
 * </ul>
 */

public class MtGoxWithdrawalRequest implements WithdrawalRequest {

  private String username;
  private String password;
  private String yubiKey;
  private String bankName;
  private String sortCode;
  private String accountNumber;
  private String iban;
  private String bic;
  private BigMoney money;

  public String getUsername() {

    return username;
  }

  public void setUsername(String username) {

    this.username = username;
  }

  public String getPassword() {

    return password;
  }

  public void setPassword(String password) {

    this.password = password;
  }

  public String getYubiKey() {

    return yubiKey;
  }

  public void setYubiKey(String yubiKey) {

    this.yubiKey = yubiKey;
  }

  public String getBankName() {

    return bankName;
  }

  public void setBankName(String bankName) {

    this.bankName = bankName;
  }

  public String getSortCode() {

    return sortCode;
  }

  public void setSortCode(String sortCode) {

    this.sortCode = sortCode;
  }

  public String getAccountNumber() {

    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {

    this.accountNumber = accountNumber;
  }

  public String getIban() {

    return iban;
  }

  public void setIban(String iban) {

    this.iban = iban;
  }

  public String getBic() {

    return bic;
  }

  public void setBic(String bic) {

    this.bic = bic;
  }

  public BigMoney getMoney() {

    return money;
  }

  public void setMoney(BigMoney money) {

    this.money = money;
  }
}
