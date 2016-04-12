package org.knowm.xchange.examples.bitstamp.trade;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.bitstamp.service.polling.BitstampTradeServiceRaw;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.bitstamp.BitstampDemoUtils;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Bitstamp exchange with authentication</li>
 * <li>get user trade history</li>
 * </ul>
 */
public class BitstampUserTradeHistoryDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitstamp = BitstampDemoUtils.createExchange();
    PollingTradeService tradeService = bitstamp.getPollingTradeService();

    generic(tradeService);
    raw((BitstampTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException {

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

  private static void raw(BitstampTradeServiceRaw tradeService) throws IOException {

    BitstampUserTransaction[] trades = tradeService.getBitstampUserTransactions(1000L);
    for (BitstampUserTransaction trade : trades) {
      System.out.println(trade);
    }

    BitstampUserTransaction[] tradesLimitedTo17 = tradeService.getBitstampUserTransactions(17L);
    for (BitstampUserTransaction trade : tradesLimitedTo17) {
      System.out.println(trade);
    }
  }

}
