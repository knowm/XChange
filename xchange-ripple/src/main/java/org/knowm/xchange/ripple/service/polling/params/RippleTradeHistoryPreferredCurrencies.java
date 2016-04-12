package org.knowm.xchange.ripple.service.polling.params;

import java.util.Collection;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;

/**
 * Convert the Ripple trade currency pairs into having these preferred base or counter currency. Preferred base currency is considered first.
 */
public interface RippleTradeHistoryPreferredCurrencies extends TradeHistoryParams {

  public Collection<Currency> getPreferredBaseCurrency();

  public Collection<Currency> getPreferredCounterCurrency();
}
