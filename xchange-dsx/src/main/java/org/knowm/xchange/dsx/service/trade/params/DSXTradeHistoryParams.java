package org.knowm.xchange.dsx.service.trade.params;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;

/**
 * @author Mikhail Wall
 */

public class DSXTradeHistoryParams extends DSXTransHistoryParams implements TradeHistoryParamCurrencyPair {

  private CurrencyPair pair;

  @Override
  public void setCurrencyPair(CurrencyPair pair) {

    this.pair = pair;
  }

  @Override
  public CurrencyPair getCurrencyPair() {

    return pair;
  }
}
