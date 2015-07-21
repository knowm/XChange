package com.xeiam.xchange.ripple.service.polling.params;

import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

/**
 * Address of the account for which the trade history is requested.
 */
public interface RippleTradeHistoryAccount extends TradeHistoryParams {

  public String getAccount();
}
