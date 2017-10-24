package org.knowm.xchange.coinfloor.service;

import java.util.Collection;
import java.util.Collections;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamMultiCurrencyPair;

public class CoinfloorOpenOrdersParams implements OpenOrdersParamMultiCurrencyPair, OpenOrdersParamCurrencyPair {
  private Collection<CurrencyPair> pairs = Collections.emptySet();
  private CurrencyPair pair = null;

  @Override
  public void setCurrencyPairs(Collection<CurrencyPair> value) {
    pairs = value;
  }

  @Override
  public Collection<CurrencyPair> getCurrencyPairs() {
    return pairs;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return pair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair value) {
    pair = value;
  }
}
