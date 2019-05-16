package org.knowm.xchange.examples.ccex.trade;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.examples.ccex.CCEXExamplesUtils;
import org.knowm.xchange.service.trade.TradeService;

public class CCEXTradesHistoryDemo {

  public static void main(String[] args) throws IOException, InterruptedException {

    Exchange exchange = CCEXExamplesUtils.getExchange();

    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException, InterruptedException {
    List<Trade> history = tradeService.getTradeHistory(null).getTrades();

    for (Trade temp : history) {
      System.out.println(temp.toString());
    }
  }
}
