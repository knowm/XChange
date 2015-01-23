package com.xeiam.xchange.examples.bitstamp.trade;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import com.xeiam.xchange.bitstamp.service.polling.BitstampTradeServiceRaw;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.bitstamp.BitstampDemoUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

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

    Trades trades = tradeService.getTradeHistory();
    System.out.println(trades);

    // Warning: using a limit here can be misleading. The underlying call
    // retrieves trades, withdrawals, and deposits. So the example here will
    // limit the result to 17 of those types and from those 17 only trades are
    // returned. It is recommended to use the raw service demonstrated below
    // if you want to use this feature.
    Trades tradesLimitedTo17 = tradeService.getTradeHistory(17L);
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
