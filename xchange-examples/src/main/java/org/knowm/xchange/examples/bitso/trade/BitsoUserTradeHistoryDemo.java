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
    Trades tradesLimitedTo17 =
        tradeService.getTradeHistory(
            DefaultTradeHistoryParamPaging.builder().pageLength(17).build());
    System.out.println(tradesLimitedTo17);
  }

  private static void raw(BitsoTradeServiceRaw tradeService) throws IOException {

    BitsoUserTransaction[] trades = tradeService.getBitsoUserTrades(1000, null, null);
    for (BitsoUserTransaction trade : trades) {
      System.out.println(trade);
    }

    BitsoUserTransaction[] tradesLimitedTo17 = tradeService.getBitsoUserTrades(17, null, null);
    for (BitsoUserTransaction trade : tradesLimitedTo17) {
      System.out.println(trade);
    }
  }
}
