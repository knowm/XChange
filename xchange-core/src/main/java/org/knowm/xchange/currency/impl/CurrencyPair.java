package org.knowm.xchange.currency.impl;

import org.knowm.xchange.currency.Currency;

/**
 * Value object to provide the following to API:
 *
 * <ul>
 *   <li>Provision of major currency symbol pairs (EUR/USD, GBP/USD etc)
 *   <li>Provision of arbitrary symbol pairs for exchange index trading, notional currencies etc
 * </ul>
 *
 * <p>Symbol pairs are quoted, for example, as EUR/USD 1.25 such that 1 EUR can be purchased with
 * 1.25 USD
 */
public class CurrencyPair implements org.knowm.xchange.currency.CurrencyPair {

  private final org.knowm.xchange.currency.Currency base;
  private final org.knowm.xchange.currency.Currency counter;

  /**
   * Full constructor In general the CurrencyPair.base is what you're wanting to buy/sell. The
   * CurrencyPair.counter is what currency you want to use to pay/receive for your purchase/sale.
   *
   * @param base The base currency is what you're wanting to buy/sell
   * @param counter The counter currency is what currency you want to use to pay/receive for your
   *     purchase/sale.
   */
  public CurrencyPair(
      org.knowm.xchange.currency.Currency base, org.knowm.xchange.currency.Currency counter) {

    this.base = base;
    this.counter = counter;
  }

  /**
   * String constructor In general the CurrencyPair.base is what you're wanting to buy/sell. The
   * CurrencyPair.counter is what currency you want to use to pay/receive for your purchase/sale.
   *
   * @param baseSymbol The base symbol is what you're wanting to buy/sell
   * @param counterSymbol The counter symbol is what currency you want to use to pay/receive for
   *     your purchase/sale.
   */
  public CurrencyPair(String baseSymbol, String counterSymbol) {

    this(
        org.knowm.xchange.currency.Currency.valueOf(baseSymbol),
        org.knowm.xchange.currency.Currency.valueOf(counterSymbol));
  }

  /**
   * Parse currency pair from a string in the same format as returned by toString() method - ABC/XYZ
   */
  public CurrencyPair(String currencyPair) {

    int split = currencyPair.indexOf('/');
    if (split < 1) {
      throw new IllegalArgumentException(
          "Could not parse currency pair from '" + currencyPair + "'");
    }
    String base = currencyPair.substring(0, split);
    String counter = currencyPair.substring(split + 1);

    this.base = org.knowm.xchange.currency.Currency.valueOf(base);
    this.counter = org.knowm.xchange.currency.Currency.valueOf(counter);
  }

  @Override
  public String toString() {
    return org.knowm.xchange.currency.CurrencyPair.getOutput(getBase(), getCounter());
  }

  public boolean contains(org.knowm.xchange.currency.Currency currency) {
    return getBase().equals(currency) || getCounter().equals(currency);
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((getBase() == null) ? 0 : getBase().hashCode());
    result = prime * result + ((getCounter() == null) ? 0 : getCounter().hashCode());
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
    org.knowm.xchange.currency.CurrencyPair other = (org.knowm.xchange.currency.CurrencyPair) obj;
    if (getBase() == null) {
      if (other.getBase() != null) {
        return false;
      }
    } else if (!getBase().equals(other.getBase())) {
      return false;
    }
    if (getCounter() == null) {
      if (other.getCounter() != null) {
        return false;
      }
    } else if (!getCounter().equals(other.getCounter())) {
      return false;
    }
    return true;
  }

  @Override
  public int compareTo(org.knowm.xchange.currency.CurrencyPair o) {

    return (getBase().compareTo(o.getBase()) << 16) + getCounter().compareTo(o.getCounter());
  }

  @Override
  public org.knowm.xchange.currency.Currency getBase() {
    return base;
  }

  @Override
  public Currency getCounter() {
    return counter;
  }
}
