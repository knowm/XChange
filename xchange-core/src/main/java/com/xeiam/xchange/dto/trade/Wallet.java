package com.xeiam.xchange.dto.trade;

import java.math.BigDecimal;

/**
 * <p>
 * DTO representing a Wallet
 * </p>
 * <p>
 * This is simply defined by an amount of money in a given currency, contained in the cash object.
 * </p>
 * <p>
 * This class is immutable.
 * </p>
 */
public final class Wallet {

  private final String currency;
  private final String description;
  private final BigDecimal balance;

  /**
   * Constructor
   * 
   * @param currency The underlying currency
   * @param balance The balance
   */
  public Wallet(String currency, BigDecimal balance) {

    this.currency = currency;
    this.balance = balance;
    this.description = "";
  }

  /**
   * Additional constructor with optional description
   * 
   * @param description Optional description to distinguish same currency Wallets
   */
  public Wallet(String currency, BigDecimal balance, String description) {

    this.currency = currency;
    this.balance = balance;
    this.description = description;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getBalance() {

    return balance;
  }

  public String getDescription() {

    return description;
  }

  @Override
  public String toString() {

    return "Wallet [currency=" + currency + ", balance=" + balance + ", description=" + description + "]";
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((balance == null) ? 0 : balance.hashCode());
    result = prime * result + ((currency == null) ? 0 : currency.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Wallet other = (Wallet) obj;
    if (balance == null) {
      if (other.balance != null) {
        return false;
      }
    } else if (!balance.equals(other.balance)) {
      return false;
    }
    if (currency == null) {
      if (other.currency != null) {
        return false;
      }
    } else if (!currency.equals(other.currency)) {
      return false;
    }
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
      return false;
    }
    return true;
  }

}
