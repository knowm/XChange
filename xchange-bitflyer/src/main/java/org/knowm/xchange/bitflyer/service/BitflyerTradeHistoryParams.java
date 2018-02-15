package org.knowm.xchange.bitflyer.service;

import java.util.Arrays;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencies;

/**
 * {@link TradeHistoryParam}.  Allows filtering to just the provided set of currencies.
 * If left <code>null</code> all currencies are included.
 * @author bryant_harris
 *
 */
public class BitflyerTradeHistoryParams implements TradeHistoryParamCurrencies {
  Currency[] currencies;
        
  @Override
  public void setCurrencies(Currency[] currencies) {
    this.currencies = currencies;
  }

  @Override
  public Currency[] getCurrencies() {
    return currencies;
  }

  @Override
  public String toString() {
      return "BitflyerTradeHistoryParams [currencies=" + Arrays.toString(currencies) + "]";
  }
}
