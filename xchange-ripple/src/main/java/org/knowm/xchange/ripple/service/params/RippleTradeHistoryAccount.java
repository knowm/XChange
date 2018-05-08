package org.knowm.xchange.ripple.service.params;

import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/** Address of the account for which the trade history is requested. */
public interface RippleTradeHistoryAccount extends TradeHistoryParams {

  String getAccount();
}
