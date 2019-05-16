package org.knowm.xchange.examples.bitstamp.trade;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.bitstamp.service.BitstampTradeHistoryParams;
import org.knowm.xchange.bitstamp.service.BitstampTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.bitstamp.BitstampDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

/**
 * Example showing the following:
 *
 * <ul>
 *   <li>Connect to Bitstamp exchange with authentication
 *   <li>get user trade history
 * </ul>
 */
public class BitstampUserTradeHistoryDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitstamp = BitstampDemoUtils.createExchange();
    TradeService tradeService = bitstamp.getTradeService();

    generic(tradeService);
    raw((BitstampTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    Trades trades = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    System.out.println(trades);

    // Warning: using a limit here can be misleading. The underlying call
    // retrieves trades, withdrawals, and deposits. So the example here will
    // limit the result to 17 of those types and from those 17 only trades are
    // returned. It is recommended to use the raw service demonstrated below
    // if you want to use this feature.
    BitstampTradeHistoryParams params =
        (BitstampTradeHistoryParams) tradeService.createTradeHistoryParams();
    params.setPageLength(17);
    params.setCurrencyPair(CurrencyPair.BTC_USD);
    Trades tradesLimitedTo17 = tradeService.getTradeHistory(params);
    System.out.println(tradesLimitedTo17);
  }

  private static void raw(BitstampTradeServiceRaw tradeService) throws IOException {

    BitstampUserTransaction[] tradesLimitedTo17 =
        tradeService.getBitstampUserTransactions(17L, CurrencyPair.BTC_USD);
    for (BitstampUserTransaction trade : tradesLimitedTo17) {
      System.out.println(trade);
    }
  }
}
