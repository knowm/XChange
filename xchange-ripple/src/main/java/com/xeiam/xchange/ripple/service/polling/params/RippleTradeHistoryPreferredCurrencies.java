package com.xeiam.xchange.ripple.service.polling.params;

import java.util.Collection;

import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

/**
 * Convert the Ripple trade currency pairs into having these preferred base or counter currency. Preferred base currency is considered first.
 */
public interface RippleTradeHistoryPreferredCurrencies extends TradeHistoryParams {

  public Collection<String> getPreferredBaseCurrency();

  public Collection<String> getPreferredCounterCurrency();
}
