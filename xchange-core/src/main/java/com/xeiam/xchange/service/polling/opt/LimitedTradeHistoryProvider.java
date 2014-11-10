package com.xeiam.xchange.service.polling.opt;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.UserTrades;

import java.io.IOException;

/**
 * Optional interface implemented by clients that support getting trade history limited to some number of entries
 */
public interface LimitedTradeHistoryProvider {

  public UserTrades getTradeHistory(long limit) throws ExchangeException, IOException;

}
