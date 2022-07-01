package org.knowm.xchange.examples.kucoin.trade;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.kucoin.KucoinExamplesUtils;
import org.knowm.xchange.kucoin.KucoinTradeHistoryParams;
import org.knowm.xchange.service.trade.TradeService;

public class KucoinTradeHistoryDemo {

  public static void main(String[] args) throws Exception {

    Exchange exchange = KucoinExamplesUtils.getExchange();

    TradeService tradeService = exchange.getTradeService();

    getRecentTrades(tradeService);
    //    getHistoricalTrades(tradeService);      // historical trades API endpoint not supported on
    // Sandbox
    getPagedTrades(tradeService);
  }

  private static void getRecentTrades(TradeService tradeService) throws IOException {
    System.out.println("Requesting trades from the last 8 days (API allows up to 8 days)...");
    KucoinTradeHistoryParams tradeHistoryParams =
        (KucoinTradeHistoryParams) tradeService.createTradeHistoryParams();
    UserTrades tradeHistory = tradeService.getTradeHistory(tradeHistoryParams);
    tradeHistory.getUserTrades().forEach(System.out::println);
  }

  private static void getHistoricalTrades(TradeService tradeService) throws IOException {
    System.out.println(
        "Requesting trades from the old API (before 18 Feb 2019 UTC+8, no time span limitation)...");
    KucoinTradeHistoryParams tradeHistoryParams =
        (KucoinTradeHistoryParams) tradeService.createTradeHistoryParams();
    Date aWhileBack =
        Date.from(
            LocalDate.of(2019, 2, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    tradeHistoryParams.setEndTime(aWhileBack);
    UserTrades tradeHistory = tradeService.getTradeHistory(tradeHistoryParams);
    tradeHistory.getUserTrades().forEach(System.out::println);
  }

  private static void getPagedTrades(TradeService tradeService) throws IOException {
    System.out.println("Requesting trades from the last 3 days...");
    KucoinTradeHistoryParams tradeHistoryParams =
        (KucoinTradeHistoryParams) tradeService.createTradeHistoryParams();

    Date threeDaysAgo =
        Date.from(
            LocalDate.now().minusDays(3).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    tradeHistoryParams.setStartTime(threeDaysAgo);
    // end time could be set here if desired
    tradeHistoryParams.setCurrencyPair(CurrencyPair.ETH_BTC);

    UserTrades tradeHistory = tradeService.getTradeHistory(tradeHistoryParams);

    tradeHistory.getUserTrades().forEach(System.out::println);
    while (tradeHistory.getNextPageCursor() != null) {
      tradeHistoryParams.setNextPageCursor(tradeHistory.getNextPageCursor());
      tradeHistory = tradeService.getTradeHistory(tradeHistoryParams);
      tradeHistory.getUserTrades().forEach(System.out::println);
    }
  }
}
