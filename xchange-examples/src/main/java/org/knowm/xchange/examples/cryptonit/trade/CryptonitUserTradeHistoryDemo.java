package org.knowm.xchange.examples.cryptonit.trade;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitUserTransaction;
import org.knowm.xchange.cryptonit2.service.CryptonitTradeHistoryParams;
import org.knowm.xchange.cryptonit2.service.CryptonitTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.cryptonit.CryptonitDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

/**
 * Example showing the following:
 *
 * <ul>
 *   <li>Connect to Bitstamp exchange with authentication
 *   <li>get user trade history
 * </ul>
 */
public class CryptonitUserTradeHistoryDemo {

  public static void main(String[] args) throws IOException {

    Exchange cryptonit = CryptonitDemoUtils.createExchange();
    TradeService tradeService = cryptonit.getTradeService();

    generic(tradeService);
    raw((CryptonitTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    Trades trades = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    System.out.println(trades);

    // Warning: using a limit here can be misleading. The underlying call
    // retrieves trades, withdrawals, and deposits. So the example here will
    // limit the result to 17 of those types and from those 17 only trades are
    // returned. It is recommended to use the raw service demonstrated below
    // if you want to use this feature.
    CryptonitTradeHistoryParams params =
        (CryptonitTradeHistoryParams) tradeService.createTradeHistoryParams();
    params.setPageLength(17);
    params.setCurrencyPair(CurrencyPair.BTC_EUR);
    Trades tradesLimitedTo17 = tradeService.getTradeHistory(params);
    System.out.println(tradesLimitedTo17);
  }

  private static void raw(CryptonitTradeServiceRaw tradeService) throws IOException {

    CryptonitUserTransaction[] tradesLimitedTo17 =
        tradeService.getCryptonitUserTransactions(17L, CurrencyPair.BTC_EUR);
    for (CryptonitUserTransaction trade : tradesLimitedTo17) {
      System.out.println(trade);
    }
  }
}
