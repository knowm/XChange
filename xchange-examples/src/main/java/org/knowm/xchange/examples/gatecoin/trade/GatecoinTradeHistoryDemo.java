package org.knowm.xchange.examples.gatecoin.trade;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.gatecoin.GatecoinDemoUtils;
import org.knowm.xchange.gatecoin.dto.trade.GatecoinTradeHistory;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinTradeHistoryResult;
import org.knowm.xchange.gatecoin.service.GatecoinTradeService;
import org.knowm.xchange.gatecoin.service.GatecoinTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

/** @author sumedha */
public class GatecoinTradeHistoryDemo {

  public static void main(String[] args) throws IOException {
    Exchange gatecoin = GatecoinDemoUtils.createExchange();
    TradeService tradeService = gatecoin.getTradeService();

    generic(tradeService);
    raw((GatecoinTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    UserTrades result = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    System.out.println("Trade history returned " + result);

    // with count parameter
    result = tradeService.getTradeHistory(new GatecoinTradeService.GatecoinTradeHistoryParams(10));
    System.out.println("Trade history returned " + result);

    // with count and tradeId parameter
    result =
        tradeService.getTradeHistory(
            new GatecoinTradeService.GatecoinTradeHistoryParams(10, "24561"));
    System.out.println("Trade history returned " + result);
  }

  private static void raw(GatecoinTradeServiceRaw tradeService) throws IOException {

    printRawTradeHistory(tradeService);
  }

  private static void printRawTradeHistory(GatecoinTradeServiceRaw tradeService)
      throws IOException {

    GatecoinTradeHistoryResult tradeHistoryResult = tradeService.getGatecoinUserTrades();
    System.out.println("Trades: " + tradeHistoryResult.getTransactions().length);
    for (GatecoinTradeHistory trade : tradeHistoryResult.getTransactions()) {
      System.out.println(trade.toString());
    }
  }
}
