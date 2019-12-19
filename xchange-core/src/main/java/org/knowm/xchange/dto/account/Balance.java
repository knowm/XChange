package org.knowm.xchange.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.Serializable;
import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DTO representing a balance in a currency
 *
 * <p>This is simply defined by an amount of money in a given currency, contained in the cash
 * object.
 *
 * <p>This class is immutable.
 */
@JsonDeserialize(builder = Balance.Builder.class)
public final class Balance implements Comparable<Balance>, Serializable {

  private static final long serialVersionUID = -1460694403597268635L;
  private static final Logger log = LoggerFactory.getLogger(Balance.class);

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
   * Constructs a balance, the {@link #available} will be the same as the <code>total</code>, and
   * the {@link #frozen} is zero. The <code>borrowed</code> and <code>loaned</code> will be zero.
   *
   * @param currency The underlying currency
   * @param total The total
   */
  public Balance(Currency currency, BigDecimal total) {

    this(
        currency,
        total,
        total,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO);
  }

  /**
   * Constructs a balance, the {@link #frozen} will be assigned as <code>total</code> - <code>
   * available</code>. The <code>borrowed</code> and <code>loaned</code> will be zero.
   *
   * @param currency the underlying currency of this balance.
   * @param total the total amount of the <code>currency</code> in this balance.
   * @param available the amount of the <code>currency</code> in this balance that is available to
   *     trade.
   */
  public Balance(Currency currency, BigDecimal total, BigDecimal available) {

    this(
        currency,
        total,
        available,
        total.add(available.negate()),
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO);
  }

  /**
   * Constructs a balance. The <code>borrowed</code> and <code>loaned</code> will be zero.
   *
   * @param currency the underlying currency of this balance.
   * @param total the total amount of the <code>currency</code> in this balance, including the
   *     <code>available</code> and <code>frozen</code>.
   * @param available the amount of the <code>currency</code> in this balance that is available to
   *     trade.
   * @param frozen the frozen amount of the <code>currency</code> in this balance that is locked in
   *     trading.
   */
  public Balance(Currency currency, BigDecimal total, BigDecimal available, BigDecimal frozen) {

    this(
        currency,
        total,
        available,
        frozen,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO);
  }

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
        log.warn(
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

  /**
   * Returns a zero balance.
   *
   * @param currency the balance currency.
   * @return a zero balance.
   */
  public static Balance zero(Currency currency) {

    return new Balance(
        currency,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO);
  }

  public Currency getCurrency() {

    return currency;
  }

  /**
   * Returns the total amount of the <code>currency</code> in this balance.
   *
   * @return the total amount.
   */
  public BigDecimal getTotal() {

    if (total == null) {
      return available.add(frozen).subtract(borrowed).add(loaned).add(withdrawing).add(depositing);
    } else {
      return total;
    }
  }

  /**
   * Returns the amount of the <code>currency</code> in this balance that is available to trade.
   *
   * @return the amount that is available to trade.
   */
  public BigDecimal getAvailable() {

    if (available == null) {
      return total
          .subtract(frozen)
          .subtract(loaned)
          .add(borrowed)
          .subtract(withdrawing)
          .subtract(depositing);
    } else {
      return available;
    }
  }

  /**
   * Returns the amount of the <code>currency</code> in this balance that may be withdrawn. Equal to
   * <code>available - borrowed</code>.
   *
   * @return the amount that is available to withdraw.
   */
  @JsonIgnore
  public BigDecimal getAvailableForWithdrawal() {

    return getAvailable().subtract(getBorrowed());
  }

  /**
   * Returns the frozen amount of the <code>currency</code> in this balance that is locked in
   * trading.
   *
   * @return the amount that is locked in open orders.
   */
  public BigDecimal getFrozen() {

    if (frozen == null) {
      return total.subtract(available);
    }
    return frozen;
  }

  /**
   * Returns the borrowed amount of the available <code>currency</code> in this balance that must be
   * repaid.
   *
   * @return the amount that must be repaid.
   */
  public BigDecimal getBorrowed() {

    return borrowed;
  }

  /**
   * Returns the loaned amount of the total <code>currency</code> in this balance that will be
   * returned.
   *
   * @return that amount that is loaned out.
   */
  public BigDecimal getLoaned() {

    return loaned;
  }

  /**
   * Returns the amount of the <code>currency</code> in this balance that is locked in withdrawal
   *
   * @return the amount in withdrawal.
   */
  public BigDecimal getWithdrawing() {

    return withdrawing;
  }

  /**
   * Returns the amount of the <code>currency</code> in this balance that is locked in deposit
   *
   * @return the amount in deposit.
   */
  public BigDecimal getDepositing() {

    return depositing;
  }

  @Override
  public String toString() {

    return "Balance [currency="
        + currency
        + ", total="
        + total
        + ", available="
        + available
        + ", frozen="
        + frozen
        + ", borrowed="
        + borrowed
        + ", loaned="
        + loaned
        + ", withdrawing="
        + withdrawing
        + ", depositing="
        + depositing
        + "]";
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((total == null) ? 0 : total.hashCode());
    result = prime * result + ((currency == null) ? 0 : currency.hashCode());
    result = prime * result + ((available == null) ? 0 : available.hashCode());
    result = prime * result + ((frozen == null) ? 0 : frozen.hashCode());
    result = prime * result + ((borrowed == null) ? 0 : borrowed.hashCode());
    result = prime * result + ((loaned == null) ? 0 : loaned.hashCode());
    result = prime * result + ((withdrawing == null) ? 0 : withdrawing.hashCode());
    result = prime * result + ((depositing == null) ? 0 : depositing.hashCode());
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
    Balance other = (Balance) obj;
    if (total == null) {
      if (other.total != null) {
        return false;
      }
    } else if (!total.equals(other.total)) {
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
    if (borrowed == null) {
      if (other.borrowed != null) {
        return false;
      }
    } else if (!borrowed.equals(other.borrowed)) {
      return false;
    }
    if (loaned == null) {
      if (other.loaned != null) {
        return false;
      }
    } else if (!loaned.equals(other.loaned)) {
      return false;
    }
    if (withdrawing == null) {
      if (other.withdrawing != null) {
        return false;
      }
    } else if (!withdrawing.equals(other.withdrawing)) {
      return false;
    }
    if (depositing == null) {
      if (other.depositing != null) {
        return false;
      }
    } else if (!depositing.equals(other.depositing)) {
      return false;
    }
    return true;
  }

  @Override
  public int compareTo(Balance other) {

    int comparison = currency.compareTo(other.currency);
    if (comparison != 0) return comparison;
    comparison = total.compareTo(other.total);
    if (comparison != 0) return comparison;
    comparison = available.compareTo(other.available);
    if (comparison != 0) return comparison;
    comparison = frozen.compareTo(other.frozen);
    if (comparison != 0) return comparison;
    comparison = borrowed.compareTo(other.borrowed);
    if (comparison != 0) return comparison;
    comparison = loaned.compareTo(other.loaned);
    if (comparison != 0) return comparison;
    comparison = withdrawing.compareTo(other.withdrawing);
    if (comparison != 0) return comparison;
    comparison = depositing.compareTo(other.depositing);
    return comparison;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {

    private Currency currency;
    private BigDecimal total;
    private BigDecimal available;
    private BigDecimal frozen;
    private BigDecimal borrowed = BigDecimal.ZERO;
    private BigDecimal loaned = BigDecimal.ZERO;
    private BigDecimal withdrawing = BigDecimal.ZERO;
    private BigDecimal depositing = BigDecimal.ZERO;

    public static Builder from(Balance balance) {

      return new Builder()
          .currency(balance.getCurrency())
          .total(balance.getTotal())
          .available(balance.getAvailable())
          .frozen(balance.getFrozen())
          .borrowed(balance.getBorrowed())
          .loaned(balance.getLoaned())
          .withdrawing(balance.getWithdrawing())
          .depositing(balance.getDepositing());
    }

    public Builder currency(Currency currency) {

      this.currency = currency;
      return this;
    }

    public Builder total(BigDecimal total) {

      this.total = total;
      return this;
    }

    public Builder available(BigDecimal available) {

      this.available = available;
      return this;
    }

    public Builder frozen(BigDecimal frozen) {

      this.frozen = frozen;
      return this;
    }

    public Builder borrowed(BigDecimal borrowed) {

      this.borrowed = borrowed;
      return this;
    }

    public Builder loaned(BigDecimal loaned) {

      this.loaned = loaned;
      return this;
    }

    public Builder withdrawing(BigDecimal withdrawing) {

      this.withdrawing = withdrawing;
      return this;
    }

    public Builder depositing(BigDecimal depositing) {

      this.depositing = depositing;
      return this;
    }

    public Balance build() {

      if (frozen == null) {
        if (total == null || available == null) {
          frozen = BigDecimal.ZERO;
        }
      }

      return new Balance(
          currency, total, available, frozen, borrowed, loaned, withdrawing, depositing);
    }
  }
}
