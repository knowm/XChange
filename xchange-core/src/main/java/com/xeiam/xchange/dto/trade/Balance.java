package com.xeiam.xchange.dto.trade;

import java.math.BigDecimal;

/**
 * <p>
 * DTO representing a balance in a currency
 * </p>
 * <p>
 * This is simply defined by an amount of money in a given currency, contained in the cash object.
 * </p>
 * <p>
 * This class is immutable.
 * </p>
 */
public final class Balance implements Comparable<Balance> {

  private final String currency;

  /**
   * @deprecated Services needing this field should migrate to the total, available, frozen fields, or return multiple accountinfos
   */
  @Deprecated
  private final String description;

  // Invariants:
  // total = available + frozen - borrowed + loaned
  // available = total - frozen - loaned + borrowed
  private final BigDecimal total;
  private final BigDecimal available;
  private final BigDecimal frozen;

  /**
   * Returns a zero balance.
   *
   * @param currency the balance currency.
   * @return a zero balance.
   */
  public static Balance zero(String currency) {
    return new Balance(currency, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }

  /**
   * Constructs a balance, the {@link #available} will be the same as the <code>total</code>, and the {@link #frozen} is zero.
   *
   * @param currency The underlying currency
   * @param total The total
   */
  public Balance(String currency, BigDecimal total) {
    this(currency, total, total, BigDecimal.ZERO);
  }

  /**
   * Constructs a balance, the {@link #frozen} will be assigned as <code>total</code> - <code>available</code>.
   *
   * @param currency the underlying currency of this balance.
   * @param total the total amount of the <code>currency</code> in this balance.
   * @param available the amount of the <code>currency</code> in this balance that is available to trade.
   */
  public Balance(String currency, BigDecimal total, BigDecimal available) {
    this(currency, total, available, total.add(available.negate()));
  }

  /**
   * Constructs a balance.
   *
   * @param currency the underlying currency of this balance.
   * @param total the total amount of the <code>currency</code> in this balance, including the <code>available</code> and the <code>frozen</code>.
   * @param available the amount of the <code>currency</code> in this balance that is available to trade.
   * @param frozen the frozen amount of the <code>currency</code> in this balance that is locked in trading.
   */
  public Balance(String currency, BigDecimal total, BigDecimal available, BigDecimal frozen) {
    this.currency = currency;
    this.total = total;
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
  public Balance(String currency, BigDecimal total, String description) {
    this(currency, total, total, BigDecimal.ZERO, description);
  }

  @Deprecated
  public Balance(String currency, BigDecimal total, BigDecimal available, String description) {
    this(currency, total, available, total.add(available.negate()), description);
  }

  @Deprecated
  public Balance(String currency, BigDecimal total, BigDecimal available, BigDecimal frozen, String description) {
    this.currency = currency;
    this.total = total;
    this.available = total;
    this.frozen = BigDecimal.ZERO;
    this.description = description;
  }

  public String getCurrency() {

    return currency;
  }

  /**
   * Returns the total amount of the <code>currency</code> in this balance.
   *
   * @return the total amount.
   */
  public BigDecimal getTotal() {

    return total;
  }

  /**
   * Returns the amount of the <code>currency</code> in this balance that is available to trade.
   *
   * @return the amount that is available to trade.
   */
  public BigDecimal getAvailable() {
    return available;
  }

  /**
   * Returns the frozen amount of the <code>currency</code> in this balance that is locked in trading.
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

    return "Balance [currency=" + currency + ", total=" + total + ", available=" + available + ", frozen=" + frozen + ", description="
        + description + "]";
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((total == null) ? 0 : total.hashCode());
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
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
      return false;
    }
    return true;
  }

  @Override
  public int compareTo(Balance other) {

    int comparison = currency.compareTo(other.currency);
    if (comparison != 0)
      return comparison;
    comparison = total.compareTo(other.total);
    if (comparison != 0)
      return comparison;
    comparison = available.compareTo(other.available);
    if (comparison != 0)
      return comparison;
    comparison = frozen.compareTo(other.frozen);
    if (comparison != 0)
      return comparison;
    comparison = description.compareTo(other.description);
    return comparison;
  }

}
