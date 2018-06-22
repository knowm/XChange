package org.knowm.xchange.examples.coingi.trade;

import java.io.IOException;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.examples.coingi.CoingiDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

public class CoingiGetOrderByIdDemo {
  public static void main(String[] args) throws IOException {
    Exchange coingi = CoingiDemoUtils.createExchange();

    TradeService tradeService = coingi.getTradeService();
    Collection<Order> orderCollection = tradeService.getOrder("Fill-an-existing-order-id-here");
    if (orderCollection != null) {
      System.out.printf("Order found: %s\n", orderCollection.iterator().next());
    } else {
      System.out.println("Order with the specified ID does not exist!");
    }
  }
}
