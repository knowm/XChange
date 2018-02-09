package org.knowm.xchange.bitcoinde.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

public interface CancelOrderByIdAndCurrencyPair extends CancelOrderParams{
  public CurrencyPair getCurrencyPair();
  public String getId();
}
