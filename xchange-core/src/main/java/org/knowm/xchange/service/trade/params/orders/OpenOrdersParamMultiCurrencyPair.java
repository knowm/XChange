package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.util.Collection;

public interface OpenOrdersParamMultiCurrencyPair extends OpenOrdersParams {
  @Override
  default boolean accept(LimitOrder order) {
    return order != null && getCurrencyPairs() != null && getCurrencyPairs().contains(order.getCurrencyPair());
  }

  void setCurrencyPairs(Collection<CurrencyPair> pairs);

  Collection<CurrencyPair> getCurrencyPairs();
}
