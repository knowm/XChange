package org.knowm.xchange.wex.v3.service.trade.params;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;

/** @author Peter N. Steinmetz Date: 4/2/15 Time: 6:54 PM */
public class WexTradeHistoryParams extends WexTransHistoryParams
    implements TradeHistoryParamCurrencyPair {

  private CurrencyPair pair;

  @Override
  public CurrencyPair getCurrencyPair() {

    return pair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {

    this.pair = pair;
  }
}
