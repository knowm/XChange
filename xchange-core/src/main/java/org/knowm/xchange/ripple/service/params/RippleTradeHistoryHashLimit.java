package org.knowm.xchange.ripple.service.params;

import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/**
 * Trade history queries (notifications and order detail) will continue until a trade with a hash matching this is found.
 */
public interface RippleTradeHistoryHashLimit extends TradeHistoryParams {

  String getHashLimit();
}
