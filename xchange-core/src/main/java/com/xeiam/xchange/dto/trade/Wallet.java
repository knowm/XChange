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

  /**
   * @deprecated
   */
  @Deprecated
  private final String description;
  private final BigDecimal balance;
  private final BigDecimal available;
  private final BigDecimal frozen;

  /**
   * Returns a wallet with zero balances.
   *
   * @param currency the wallet currency.
   * @return a wallet with zero balances.
   */
  public static Wallet zero(String currency) {
    return new Wallet(currency, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }

  /**
   * Constructs a wallet, the {@link #available} will be the same as the <code>balance</code>, and the {@link #frozen} is zero.
   *
   * @param currency The underlying currency
   * @param balance The balance
   */
  public Wallet(String currency, BigDecimal balance) {
    this(currency, balance, balance, BigDecimal.ZERO);
  }

  /**
   * Constructs a wallet, the {@link #frozen} will be assigned as <code>balance</code> - <code>available</code>.
   *
   * @param currency the underlying currency of this wallet.
   * @param balance the total amount of the <code>currency</code> in this wallet.
   * @param available the amount of the <code>currency</code> in this wallet that is available to trade.
   */
  public Wallet(String currency, BigDecimal balance, BigDecimal available) {
    this(currency, balance, available, balance.add(available.negate()));
  }

  /**
   * Constructs a wallet.
   *
   * @param currency the underlying currency of this wallet.
   * @param balance the total amount of the <code>currency</code> in this wallet, including the <code>available</code> and the <code>frozen</code>.
   * @param available the amount of the <code>currency</code> in this wallet that is available to trade.
   * @param frozen the frozen amount of the <code>currency</code> in this wallet that is locked in trading.
   */
  public Wallet(String currency, BigDecimal balance, BigDecimal available, BigDecimal frozen) {
    this.currency = currency;
    this.balance = balance;
    this.available = available;
    this.frozen = frozen;
    this.description = "";
  }

  /**
   * Additional constructor with optional description
   *
   * @param description Optional description to distinguish same currency Wallets
   */
  @Deprecated
  public Wallet(String currency, BigDecimal balance, String description) {
    this(currency, balance, balance, BigDecimal.ZERO, description);
  }

  @Deprecated
  public Wallet(String currency, BigDecimal balance, BigDecimal available, String description) {
    this(currency, balance, available, balance.add(available.negate()), description);
  }

  @Deprecated
  public Wallet(String currency, BigDecimal balance, BigDecimal available, BigDecimal frozen, String description) {
    this.currency = currency;
    this.balance = balance;
    this.available = balance;
    this.frozen = BigDecimal.ZERO;
    this.description = description;
  }

  public String getCurrency() {

    return currency;
  }

  /**
   * Returns the total amount of the <code>currency</code> in this wallet.
   *
   * @return the total amount.
   */
  public BigDecimal getBalance() {

    return balance;
  }

  /**
   * Returns the amount of the <code>currency</code> in this wallet that is available to trade.
   *
   * @return the amount that is available to trade.
   */
  public BigDecimal getAvailable() {
    return available;
  }

  /**
   * Returns the frozen amount of the <code>currency</code> in this wallet that is locked in trading.
   *
   * @return the amount that is locked in open orders.
   */
  public BigDecimal getFrozen() {
    return frozen;
  }

  /**
   * @deprecated
   */
  @Deprecated
  public String getDescription() {

    return description;
  }

  @Override
  public String toString() {

    return "Wallet [currency=" + currency + ", balance=" + balance + ", available=" + available + ", frozen=" + frozen + ", description="
        + description + "]";
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((balance == null) ? 0 : balance.hashCode());
    result = prime * result + ((currency == null) ? 0 : currency.hashCode());
    result = prime * result + ((available == null) ? 0 : available.hashCode());
    result = prime * result + ((frozen == null) ? 0 : frozen.hashCode());
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
    if (available == null) {
      if (other.available != null) {
        return false;
      }
    } else if (!available.equals(other.available)) {
      return false;
    }
    if (frozen == null) {
      if (other.frozen != null) {
        return false;
      }
    } else if (!frozen.equals(other.frozen)) {
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
