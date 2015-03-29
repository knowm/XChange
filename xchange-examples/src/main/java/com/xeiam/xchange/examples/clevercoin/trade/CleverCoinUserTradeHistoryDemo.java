package com.xeiam.xchange.examples.clevercoin.trade;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.clevercoin.dto.trade.CleverCoinUserTransaction;
import com.xeiam.xchange.clevercoin.service.polling.CleverCoinTradeServiceRaw;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.clevercoin.CleverCoinDemoUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to CleverCoin exchange with authentication</li>
 * <li>get user trade history</li>
 * </ul>
 */
public class CleverCoinUserTradeHistoryDemo {

  public static void main(String[] args) throws IOException {

    Exchange cleverCoin = CleverCoinDemoUtils.createExchange();
    PollingTradeService tradeService = cleverCoin.getPollingTradeService();

    generic(tradeService);
    raw((CleverCoinTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException {

    Trades trades = tradeService.getTradeHistory(150);
    System.out.println(trades);

    // Warning: using a limit here can be misleading. The underlying call
    // retrieves trades, withdrawals, and deposits. So the example here will
    // limit the result to 17 of those types and from those 17 only trades are
    // returned. It is recommended to use the raw service demonstrated below
    // if you want to use this feature.
    //Trades tradesLimitedTo17 = tradeService.getTradeHistory();
    //System.out.println(tradesLimitedTo17);
  }

  private static void raw(CleverCoinTradeServiceRaw tradeService) throws IOException {

    CleverCoinUserTransaction[] trades = tradeService.getCleverCoinUserTransactions(200);
    for (CleverCoinUserTransaction trade : trades) {
      System.out.println(trade);
    }
  }

}
