package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

public interface OpenOrdersParamCurrencyPair extends OpenOrdersParams {
  @Override
  default boolean accept(LimitOrder order) {
    return order != null
        && getCurrencyPair() != null
        && getCurrencyPair().equals(order.getCurrencyPair());
  }

  CurrencyPair getCurrencyPair();

  void setCurrencyPair(CurrencyPair pair);
}
