package org.knowm.xchange.btce.v3.service.trade.params;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;

/**
 * @author Peter N. Steinmetz Date: 4/2/15 Time: 6:54 PM
 */
public class BTCETradeHistoryParams extends BTCETransHistoryParams implements TradeHistoryParamCurrencyPair {

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
