package org.knowm.xchange.examples.coingi.trade;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.coingi.CoingiDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

public class CoingiGetOpenOrdersDemo {
  public static void main(String[] args) throws IOException {
    Exchange coingi = CoingiDemoUtils.createExchange();

    TradeService tradeService = coingi.getTradeService();
    OpenOrders openOrders = tradeService.getOpenOrders();
    openOrders.getAllOpenOrders().forEach(System.out::println);
    System.out.printf("Received %d open orders.\n", openOrders.getAllOpenOrders().size());
  }
}
