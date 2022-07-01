package org.knowm.xchange.examples.lgo.trade;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.lgo.LgoExamplesUtils;
import org.knowm.xchange.lgo.service.LgoTradeHistoryParams;
import org.knowm.xchange.service.trade.TradeService;

public class LgoTradeHistoryDemo {

  public static void main(String[] args) throws IOException {
    Exchange lgoExchange = LgoExamplesUtils.getExchange();
    TradeService tradeService = lgoExchange.getTradeService();
    UserTrades tradeHistory = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    System.out.println("Your last trades:");
    print(tradeHistory);
    while (tradeHistory.getNextPageCursor() != null
        && !tradeHistory.getNextPageCursor().isEmpty()) {
      LgoTradeHistoryParams params =
          (LgoTradeHistoryParams) tradeService.createTradeHistoryParams();
      params.setNextPageCursor(tradeHistory.getNextPageCursor());
      tradeHistory = tradeService.getTradeHistory(params);
      print(tradeHistory);
    }
  }

  private static void print(UserTrades tradeHistory) {
    tradeHistory.getUserTrades().stream().map(UserTrade::toString).forEach(System.out::println);
  }
}
