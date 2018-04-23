package org.knowm.xchange.dto.account.impl;

import java.math.BigDecimal;
import java.util.Objects;
import org.knowm.xchange.currency.Currency;

/**
 * DTO representing a balance in a currency
 *
 * <p>This is simply defined by an amount of money in a given currency, contained in the cash
 * object.
 *
 * <p>This class is immutable.
 */
public final class Balance implements org.knowm.xchange.dto.account.Balance {

  private final Currency currency;

  // Invariant:
  // total = available + frozen - borrowed + loaned + withdrawing + depositing;
  private final BigDecimal total;
  private final BigDecimal available;
  private final BigDecimal frozen;
  private final BigDecimal loaned;
  private final BigDecimal borrowed;
  private final BigDecimal withdrawing;
  private final BigDecimal depositing;

  /**
   * Constructs a balance.
   *
   * @param currency the underlying currency of this balance.
   * @param total the total amount of the <code>currency</code> in this balance, equal to <code>
   *     available + frozen - borrowed + loaned</code>.
   * @param available the amount of the <code>currency</code> in this balance that is available to
   *     trade, including the <code>borrowed</code>.
   * @param frozen the frozen amount of the <code>currency</code> in this balance that is locked in
   *     trading.
   * @param borrowed the borrowed amount of the available <code>currency</code> in this balance that
   *     must be repaid.
   * @param loaned the loaned amount of the total <code>currency</code> in this balance that will be
   *     returned.
   * @param withdrawing the amount of the <code>currency</code> in this balance that is scheduled
   *     for withdrawal.
   * @param depositing the amount of the <code>currency</code> in this balance that is being
   *     deposited but not available yet.
   */
  public Balance(
      Currency currency,
      BigDecimal total,
      BigDecimal available,
      BigDecimal frozen,
      BigDecimal borrowed,
      BigDecimal loaned,
      BigDecimal withdrawing,
      BigDecimal depositing) {

    if (total != null && available != null) {
      BigDecimal sum =
          available.add(frozen).subtract(borrowed).add(loaned).add(withdrawing).add(depositing);
      if (0 != total.compareTo(sum)) {
        org.knowm.xchange.dto.account.Balance.getLog()
            .warn(
                "{} = total != available + frozen - borrowed + loaned + withdrawing + depositing = {}",
                total,
                sum);
      }
    }
    this.currency = currency;
    this.total = total;
    this.available = available;
    this.frozen = frozen;
    this.borrowed = borrowed;
    this.loaned = loaned;
    this.withdrawing = withdrawing;
    this.depositing = depositing;
  }

  @Override
  public Currency getCurrency() {
    return currency;
  }

  /**
   * Returns the total amount of the <code>currency</code> in this balance.
   *
   * @return the total amount.
   */
  @Override
  public BigDecimal getTotal() {

    return total == null ? inferTotal() : total;
  }

  @Override
  public BigDecimal inferTotal() {
    return getAvailable()
        .add(getFrozen())
        .subtract(getBorrowed())
        .add(getLoaned())
        .add(getWithdrawing())
        .add(getDepositing());
  }

  /**
   * Returns the amount of the <code>currency</code> in this balance that is available to trade.
   *
   * @return the amount that is available to trade.
   */
  @Override
  public BigDecimal getAvailable() {

    return available == null ? inferAvailable() : available;
  }

  /**
   * Returns the frozen amount of the <code>currency</code> in this balance that is locked in
   * trading.
   *
   * @return the amount that is locked in open orders.
   */
  @Override
  public BigDecimal getFrozen() {
    return frozen == null ? inferFrozen() : frozen;
  }

  /**
   * Returns the borrowed amount of the available <code>currency</code> in this balance that must be
   * repaid.
   *
   * @return the amount that must be repaid.
   */
  @Override
  public BigDecimal getBorrowed() {
    return borrowed;
  }

  /**
   * Returns the loaned amount of the total <code>currency</code> in this balance that will be
   * returned.
   *
   * @return that amount that is loaned out.
   */
  @Override
  public BigDecimal getLoaned() {

    return loaned;
  }

  /**
   * Returns the amount of the <code>currency</code> in this balance that is locked in withdrawal
   *
   * @return the amount in withdrawal.
   */
  @Override
  public BigDecimal getWithdrawing() {

    return withdrawing;
  }

  /**
   * Returns the amount of the <code>currency</code> in this balance that is locked in deposit
   *
   * @return the amount in deposit.
   */
  @Override
  public BigDecimal getDepositing() {

    return depositing;
  }

  @Override
  public String toString() {

    return "Balance [currency="
        + getCurrency()
        + ", total="
        + getTotal()
        + ", available="
        + getAvailable()
        + ", frozen="
        + getFrozen()
        + ", borrowed="
        + getBorrowed()
        + ", loaned="
        + getLoaned()
        + ", withdrawing="
        + getWithdrawing()
        + ", depositing="
        + getDepositing()
        + "]";
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        this.getCurrency(),
        this.getTotal(),
        this.getAvailable(),
        this.getFrozen(),
        this.getLoaned(),
        this.getBorrowed(),
        this.getWithdrawing(),
        this.getDepositing());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Balance)) return false;
    org.knowm.xchange.dto.account.Balance balance = (org.knowm.xchange.dto.account.Balance) o;
    return Objects.equals(this.getCurrency(), balance.getCurrency())
        && Objects.equals(this.getTotal(), balance.getTotal())
        && Objects.equals(this.getAvailable(), balance.getAvailable())
        && Objects.equals(this.getFrozen(), balance.getFrozen())
        && Objects.equals(this.getLoaned(), balance.getLoaned())
        && Objects.equals(this.getBorrowed(), balance.getBorrowed())
        && Objects.equals(this.getWithdrawing(), balance.getWithdrawing())
        && Objects.equals(this.getDepositing(), balance.getDepositing());
  }
}
