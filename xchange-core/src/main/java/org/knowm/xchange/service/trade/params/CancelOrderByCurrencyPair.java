package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.currency.CurrencyPair;

public interface CancelOrderByCurrencyPair extends CancelOrderParams {

  public CurrencyPair getCurrencyPair();
}
