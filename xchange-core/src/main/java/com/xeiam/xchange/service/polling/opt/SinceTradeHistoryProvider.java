package com.xeiam.xchange.service.polling.opt;

import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;

import java.io.IOException;

/**
 * Optional interface implemented by exchange clients that support getting trade history starting with the timestamp or the ID of the previous trade.
 */
public interface SinceTradeHistoryProvider {

  public UserTrades getTradeHistory(UserTrade last) throws IOException;
}
