package org.knowm.xchange.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Balance extends Comparable<Balance>, Serializable {
  Logger log = LoggerFactory.getLogger(org.knowm.xchange.dto.account.impl.Balance.class);

  /**
   * Returns a zero balance. jackson factory method
   *
   * @param currency the balance currency.
   * @return a zero balance.
   */
  static Balance valueOf(Currency currency) {

    return new Builder()
        .setCurrency(currency)
        .setTotal(BigDecimal.ZERO)
        .setAvailable(BigDecimal.ZERO)
        .setFrozen(BigDecimal.ZERO)
        .createBalance();
  }

  static Logger getLog() {
    return log;
  }

  static Balance build(
      Currency currency,
      BigDecimal total,
      BigDecimal available,
      BigDecimal frozen,
      BigDecimal borrowed,
      BigDecimal loaned,
      BigDecimal withdrawing,
      BigDecimal depositing) {
    return new org.knowm.xchange.dto.account.impl.Balance(
        currency, total, available, frozen, borrowed, loaned, withdrawing, depositing);
  }

  Currency getCurrency();

  /**
   * Returns the total amount of the <code>currency</code> in this balance.
   *
   * @return the total amount.
   */
  BigDecimal getTotal();

  BigDecimal inferTotal();

  /**
   * Returns the amount of the <code>currency</code> in this balance that is available to trade.
   *
   * @return the amount that is available to trade.
   */
  BigDecimal getAvailable();

  default BigDecimal inferAvailable() {
    return getTotal()
        .subtract(getFrozen())
        .subtract(getLoaned())
        .add(getBorrowed())
        .subtract(getWithdrawing())
        .subtract(getDepositing());
  }

  /**
   * Returns the amount of the <code>currency</code> in this balance that may be withdrawn. Equal to
   * <code>available - borrowed</code>.
   *
   * @return the amount that is available to withdraw.
   */
  default BigDecimal getAvailableForWithdrawal() {

    return getAvailable().subtract(getBorrowed());
  }
  /**
   * Returns the frozen amount of the <code>currency</code> in this balance that is locked in
   * trading.
   *
   * @return the amount that is locked in open orders.
   */
  BigDecimal getFrozen();

  default BigDecimal inferFrozen() {
    return getTotal().subtract(getAvailable());
  }

  /**
   * Returns the borrowed amount of the available <code>currency</code> in this balance that must be
   * repaid.
   *
   * @return the amount that must be repaid.
   */
  BigDecimal getBorrowed();

  /**
   * Returns the loaned amount of the total <code>currency</code> in this balance that will be
   * returned.
   *
   * @return that amount that is loaned out.
   */
  BigDecimal getLoaned();

  /**
   * Returns the amount of the <code>currency</code> in this balance that is locked in withdrawal
   *
   * @return the amount in withdrawal.
   */
  BigDecimal getWithdrawing();

  /**
   * Returns the amount of the <code>currency</code> in this balance that is locked in deposit
   *
   * @return the amount in deposit.
   */
  BigDecimal getDepositing();

  public default int compareTo(Balance other) {

    int comparison = getCurrency().compareTo(other.getCurrency());
    if (comparison != 0) return comparison;
    comparison = getTotal().compareTo(other.getTotal());
    if (comparison != 0) return comparison;
    comparison = getAvailable().compareTo(other.getAvailable());
    if (comparison != 0) return comparison;
    comparison = getFrozen().compareTo(other.getFrozen());
    if (comparison != 0) return comparison;
    comparison = getBorrowed().compareTo(other.getBorrowed());
    if (comparison != 0) return comparison;
    comparison = getLoaned().compareTo(other.getLoaned());
    if (comparison != 0) return comparison;
    comparison = getWithdrawing().compareTo(other.getWithdrawing());
    if (comparison != 0) return comparison;
    comparison = getDepositing().compareTo(other.getDepositing());
    return comparison;
  }

  class Builder {

    private Currency currency;
    private BigDecimal total;
    private BigDecimal available;
    private BigDecimal frozen = BigDecimal.ZERO;
    private BigDecimal borrowed = BigDecimal.ZERO;
    private BigDecimal loaned = BigDecimal.ZERO;
    private BigDecimal withdrawing = BigDecimal.ZERO;
    private BigDecimal depositing = BigDecimal.ZERO;

    public static Builder from(Balance balance) {

      return new Builder()
          .setCurrency(balance.getCurrency())
          .setAvailable(balance.getAvailable())
          .setFrozen(balance.getFrozen())
          .setBorrowed(balance.getBorrowed())
          .setLoaned(balance.getLoaned())
          .setWithdrawing(balance.getWithdrawing())
          .setDepositing(balance.getDepositing());
    }

    public Builder setCurrency(Currency currency) {

      this.currency = currency;
      return this;
    }

    public Builder setTotal(BigDecimal total) {

      this.total = total;
      return this;
    }

    public Builder setAvailable(BigDecimal available) {

      this.available = available;
      return this;
    }

    public Builder setFrozen(BigDecimal frozen) {

      this.frozen = frozen;
      return this;
    }

    public Builder setBorrowed(BigDecimal borrowed) {

      this.borrowed = borrowed;
      return this;
    }

    public Builder setLoaned(BigDecimal loaned) {

      this.loaned = loaned;
      return this;
    }

    public Builder setWithdrawing(BigDecimal withdrawing) {

      this.withdrawing = withdrawing;
      return this;
    }

    public Builder setDepositing(BigDecimal depositing) {

      this.depositing = depositing;
      return this;
    }

    public Balance createBalance() {

      if (frozen == null) {
        if (total == null || available == null) {
          frozen = BigDecimal.ZERO;
        }
      }

      return build(currency, total, available, frozen, borrowed, loaned, withdrawing, depositing);
    }
  }
}
