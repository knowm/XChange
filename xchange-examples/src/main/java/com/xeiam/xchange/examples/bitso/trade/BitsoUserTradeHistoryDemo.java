package com.xeiam.xchange.examples.bitso.trade;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitso.dto.trade.BitsoUserTransaction;
import com.xeiam.xchange.bitso.service.polling.BitsoTradeServiceRaw;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.bitso.BitsoDemoUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Bitso exchange with authentication</li>
 * <li>get user trade history</li>
 * </ul>
 */
public class BitsoUserTradeHistoryDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitso = BitsoDemoUtils.createExchange();
    PollingTradeService tradeService = bitso.getPollingTradeService();

    generic(tradeService);
    raw((BitsoTradeServiceRaw) tradeService);
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
