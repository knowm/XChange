package org.knowm.xchange.examples.dsx.trade;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.dsx.DSXExamplesUtils;
import org.knowm.xchange.service.trade.TradeService;

/**
 * @author Mikhail Wall
 */
public class DSXTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange dsx = DSXExamplesUtils.createExchange();

  }

  private static void generic(Exchange exchange) throws IOException {

    TradeService tradeService = exchange.getTradeService();


  }

  private static void printOpenOrders(TradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }
}
