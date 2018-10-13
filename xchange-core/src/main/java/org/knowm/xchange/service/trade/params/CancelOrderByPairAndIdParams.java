package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.currency.CurrencyPair;

public interface CancelOrderByPairAndIdParams extends CancelOrderByIdParams {
  CurrencyPair getCurrencyPair();
}
