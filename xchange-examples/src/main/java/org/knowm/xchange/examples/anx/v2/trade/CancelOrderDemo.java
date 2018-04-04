package org.knowm.xchange.examples.anx.v2.trade;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.anx.v2.ANXExamplesUtils;
import org.knowm.xchange.service.trade.TradeService;

/** Test placing a limit order at ANX */
public class CancelOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the private trading functionality (authentication)
    TradeService tradeService = anx.getTradeService();

    boolean success = tradeService.cancelOrder("5aaef0f5-8c90-4a93-a097-0bad2dd475c5");
    System.out.println("success= " + success);

    // get open orders
    OpenOrders openOrders = tradeService.getOpenOrders();
    for (LimitOrder openOrder : openOrders.getOpenOrders()) {
      System.out.println(openOrder.toString());
    }
  }
}
