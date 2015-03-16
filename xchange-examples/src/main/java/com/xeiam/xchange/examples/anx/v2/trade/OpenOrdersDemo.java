package com.xeiam.xchange.examples.anx.v2.trade;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

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
