package org.knowm.xchange.examples.ccex.trade;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.ccex.CCEXExamplesUtils;
import org.knowm.xchange.service.trade.TradeService;

public class CCEXOpenOrdersDemo {

  public static void main(String[] args) throws IOException, InterruptedException {

    Exchange exchange = CCEXExamplesUtils.getExchange();

    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException, InterruptedException {
    List<LimitOrder> limitOrders = tradeService.getOpenOrders().getOpenOrders();

    for (LimitOrder temp : limitOrders) {
      System.out.println(temp.toString());
    }
  }
}
