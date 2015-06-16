package com.xeiam.xchange.examples.taurus.trade;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsSorted;
import com.xeiam.xchange.taurus.dto.trade.TaurusUserTransaction;
import com.xeiam.xchange.taurus.service.polling.TaurusTradeServiceRaw;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.taurus.TaurusDemoUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Taurus exchange with authentication</li>
 * <li>get user trade history</li>
 * </ul>
 */
public class TaurusUserTradeHistoryDemo {

  public static void main(String[] args) throws IOException {

    Exchange taurus = TaurusDemoUtils.createExchange();
    PollingTradeService tradeService = taurus.getPollingTradeService();

    generic(tradeService);
    raw((TaurusTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException {

    Trades trades = tradeService.getTradeHistory();
    System.out.println(trades);

    // Warning: using a limit here can be misleading. The underlying call
    // retrieves trades, withdrawals, and deposits. So the example here will
    // limit the result to 3 of those types and from those 3 only trades are
    // returned. It is recommended to use the raw service demonstrated below
    // if you want to use this feature.
    final TradeHistoryParams params = tradeService.createTradeHistoryParams();
    ((TradeHistoryParamPaging)params).setPageLength(3);
    Trades tradesLimitedTo3 = tradeService.getTradeHistory(params);
    System.out.println(tradesLimitedTo3);
  }

  private static void raw(TaurusTradeServiceRaw tradeService) throws IOException {

    TaurusUserTransaction[] trades = tradeService.getTaurusUserTransactions(null, 1000, TradeHistoryParamsSorted.Order.asc);
    for (TaurusUserTransaction trade : trades) {
      System.out.println(trade);
    }

    TaurusUserTransaction[] transactionLimitedTo3 = tradeService.getTaurusUserTransactions(null, 3, null);
    for (TaurusUserTransaction tx : transactionLimitedTo3) {
      System.out.println(tx);
    }
  }
}
