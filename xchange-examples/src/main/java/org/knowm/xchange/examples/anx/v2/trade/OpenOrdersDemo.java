package org.knowm.xchange.examples.anx.v2.trade;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.anx.v2.ANXExamplesUtils;
import org.knowm.xchange.service.polling.trade.PollingTradeService;

/**
 * Test requesting all open orders at MtGox
 */
public class OpenOrdersDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the private trading functionality (authentication)
    PollingTradeService tradeService = anx.getPollingTradeService();

    // Get the open orders
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders.toString());
  }

}
