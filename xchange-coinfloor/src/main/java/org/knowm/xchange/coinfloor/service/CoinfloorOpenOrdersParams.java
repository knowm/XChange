package org.knowm.xchange.coinfloor.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamMultiCurrencyPair;

import java.util.Collection;
import java.util.Collections;

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

  @Override
  public boolean accept(LimitOrder order) {
    return OpenOrdersParamCurrencyPair.super.accept(order) || OpenOrdersParamMultiCurrencyPair.super.accept(order);
  }
}
