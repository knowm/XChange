package org.knowm.xchange.currency.impl;

import org.knowm.xchange.currency.CurrencyAttributes;

/**
 * A Currency class roughly modeled after {@link java.util.Currency}. Each object retains the code
 * it was acquired with -- so {@link Currency #valueOf}("BTC").{@link #getCurrencyCode}() will
 * always be "BTC", even though the proposed ISO 4217 code is "XBT"
 */
public class Currency implements org.knowm.xchange.currency.Currency {

  private final String code;
  private final CurrencyAttributes attributes;
  /** Public handle constructor. Links to an existing currency. */
  public Currency(String code) {
    this.code = code;
    this.attributes = org.knowm.xchange.currency.Currency.valueOf(code).getAttributes();
  }

  public Currency(String alternativeCode, CurrencyAttributes attributes) {
    this.code = alternativeCode;
    this.attributes = attributes;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public CurrencyAttributes getAttributes() {
    return attributes;
  }

  @Override
  public String toString() {

    return this.getCode();
  }

  public int hashCode() {

    return this.getAttributes().hashCode();
  }

  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    Currency other = (Currency) obj;

    return this.getAttributes().equals(other.getAttributes());
  }
}
