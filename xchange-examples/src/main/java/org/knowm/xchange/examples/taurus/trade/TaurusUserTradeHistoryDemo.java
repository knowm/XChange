package org.knowm.xchange.examples.taurus.trade;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.taurus.TaurusDemoUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.taurus.dto.trade.TaurusUserTransaction;
import org.knowm.xchange.taurus.service.TaurusTradeServiceRaw;

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
    TradeService tradeService = taurus.getTradeService();

    generic(tradeService);
    raw((TaurusTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    Trades trades = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    System.out.println(trades);

    // Warning: using a limit here can be misleading. The underlying call
    // retrieves trades, withdrawals, and deposits. So the example here will
    // limit the result to 3 of those types and from those 3 only trades are
    // returned. It is recommended to use the raw service demonstrated below
    // if you want to use this feature.
    final TradeHistoryParams params = tradeService.createTradeHistoryParams();
    ((TradeHistoryParamPaging) params).setPageLength(3);
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
