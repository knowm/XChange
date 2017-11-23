package org.knowm.xchange.service.trade.params.orders;

import java.util.Collection;
import java.util.Collections;

import org.knowm.xchange.currency.CurrencyPair;

public class DefaultOpenOrdersParamMultiCurrencyPair implements OpenOrdersParamMultiCurrencyPair {

  private Collection<CurrencyPair> pairs = Collections.emptySet();

  @Override
  public void setCurrencyPairs(Collection<CurrencyPair> value) {
    pairs = value;
  }

  @Override
  public Collection<CurrencyPair> getCurrencyPairs() {
    return pairs;
  }
}
