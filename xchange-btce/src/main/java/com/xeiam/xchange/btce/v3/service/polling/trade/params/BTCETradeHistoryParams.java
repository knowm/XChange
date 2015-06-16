package com.xeiam.xchange.btce.v3.service.polling.trade.params;

import com.xeiam.xchange.btce.v3.BTCEAuthenticated;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsIdSpan;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;

import java.util.Date;

/**
 * @author Peter N. Steinmetz
 *         Date: 4/2/15
 *         Time: 6:54 PM
 */
public class BTCETradeHistoryParams extends BTCETransHistoryParams implements
   TradeHistoryParamCurrencyPair {

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
