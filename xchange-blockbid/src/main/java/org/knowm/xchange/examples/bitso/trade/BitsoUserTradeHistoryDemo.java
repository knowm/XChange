package org.knowm.xchange.examples.bitso.trade;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.dto.trade.BitsoUserTransaction;
import org.knowm.xchange.bitso.service.BitsoTradeServiceRaw;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.bitso.BitsoDemoUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;

/**
 * Example showing the following:
 *
 * <ul>
 *   <li>Connect to Bitso exchange with authentication
 *   <li>get user trade history
 * </ul>
 */
public class BitsoUserTradeHistoryDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitso = BitsoDemoUtils.createExchange();
    TradeService tradeService = bitso.getTradeService();

    generic(tradeService);
    raw((BitsoTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    Trades trades = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    System.out.println(trades);

    // Warning: using a limit here can be misleading. The underlying call
    // retrieves trades, withdrawals, and deposits. So the example here will
    // limit the result to 17 of those types and from those 17 only trades are
    // returned. It is recommended to use the raw service demonstrated below
    // if you want to use this feature.
    Trades tradesLimitedTo17 = tradeService.getTradeHistory(new DefaultTradeHistoryParamPaging(17));
    System.out.println(tradesLimitedTo17);
  }

  private static void raw(BitsoTradeServiceRaw tradeService) throws IOException {

    BitsoUserTransaction[] trades = tradeService.getBitsoUserTransactions(1000L);
    for (BitsoUserTransaction trade : trades) {
      System.out.println(trade);
    }

    BitsoUserTransaction[] tradesLimitedTo17 = tradeService.getBitsoUserTransactions(17L);
    for (BitsoUserTransaction trade : tradesLimitedTo17) {
      System.out.println(trade);
    }
  }
}
