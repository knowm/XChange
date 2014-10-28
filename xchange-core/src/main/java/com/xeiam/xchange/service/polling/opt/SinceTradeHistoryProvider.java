package com.xeiam.xchange.service.polling.opt;

import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;

import java.io.IOException;

/**
 * Optional interface implemented by clients that support getting trade history limited to some number of entries
 */
public interface SinceTradeHistoryProvider {

  public UserTrades getTradeHistory(UserTrade last) throws IOException;
}
