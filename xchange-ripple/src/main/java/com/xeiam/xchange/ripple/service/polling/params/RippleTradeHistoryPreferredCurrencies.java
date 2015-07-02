package com.xeiam.xchange.ripple.service.polling.params;

import java.util.Collection;

/**
 * Convert the Ripple trade currency pairs into having these preferred base or counter currency. Preferred base currency is considered first.
 */
public interface RippleTradeHistoryPreferredCurrencies {

  public void addPreferredBaseCurrency(final String value);
  public Collection<String> getPreferredBaseCurrency();

  public void addPreferredCounterCurrency(final String value);
  public Collection<String> getPreferredCounterCurrency();
}
