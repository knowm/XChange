package org.knowm.xchange.examples.therock.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.examples.therock.TheRockExampleUtils;
import org.knowm.xchange.therock.TheRock;
import org.knowm.xchange.therock.dto.trade.TheRockOrder;
import org.knowm.xchange.therock.dto.trade.TheRockOrder.Side;
import org.knowm.xchange.therock.dto.trade.TheRockOrder.Type;
import org.knowm.xchange.therock.dto.trade.TheRockOrders;
import org.knowm.xchange.therock.service.TheRockTradeServiceRaw;

public class TheRockTradeRawDemo {

  public static final CurrencyPair BTC_EUR = CurrencyPair.BTC_EUR;

  public static void main(String[] args) throws Exception {
    Exchange theRockExchange = TheRockExampleUtils.createTestExchange();
    raw(theRockExchange);
  }

  private static void raw(Exchange theRockExchange) throws IOException, InterruptedException {
    TheRockTradeServiceRaw tradeService =
        (TheRockTradeServiceRaw) theRockExchange.getTradeService();

    // create order
    BigDecimal amount = new BigDecimal("0.01");
    BigDecimal price = new BigDecimal("50.0");
    TheRock.Pair pair = new TheRock.Pair(BTC_EUR);
    TheRockOrder order = new TheRockOrder(pair, Side.buy, Type.limit, amount, price);
    TheRockOrder orderResult = tradeService.placeTheRockOrder(BTC_EUR, order);
    print(orderResult);
    Thread.sleep(3000);

    // show-order
    orderResult = tradeService.showTheRockOrder(BTC_EUR, orderResult.getId());
    print(orderResult);
    Thread.sleep(3000);

    // get-orders (without any parameters besides pair, only open orders will be returned)
    TheRockOrders orders = tradeService.getTheRockOrders(BTC_EUR);
    print(orders);
    Thread.sleep(3000);

    // get execute orders, only page 1
    TheRockOrders executedOrders =
        tradeService.getTheRockOrders(BTC_EUR, null, null, "executed", null, null, 1);
    print(executedOrders);
    Thread.sleep(3000);

    // get only executed sell order, starting on page 3
    TheRockOrders execSellOrders =
        tradeService.getTheRockOrders(BTC_EUR, null, null, "executed", Side.sell, null, 1);
    print(execSellOrders);
    Thread.sleep(3000);

    // cancel
    tradeService.cancelTheRockOrder(BTC_EUR, orderResult.getId());
    Thread.sleep(3000);
  }

  private static void print(Object object) {
    System.out.println();
  }
}
