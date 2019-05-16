package org.knowm.xchange.examples.bibox.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.service.BiboxTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.bibox.BiboxExamplesUtils;
import org.knowm.xchange.service.trade.TradeService;

public class BiboxTradeDemo {

  private static final LimitOrder LIMIT_ORDER =
      new LimitOrder.Builder(OrderType.ASK, CurrencyPair.ETC_ETH)
          .limitPrice(new BigDecimal("100.0"))
          .originalAmount(new BigDecimal(".1"))
          .build();

  public static void main(String[] args) throws IOException {

    Exchange exchange = BiboxExamplesUtils.getExchange();

    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((BiboxTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {
    try {
      String uuid = tradeService.placeLimitOrder(LIMIT_ORDER);
      System.out.println("Order successfully placed. ID=" + uuid);

      Thread.sleep(1000); // wait for order to propagate

      System.out.println(tradeService.getOpenOrders());

      System.out.println("Attempting to cancel order " + uuid);
      boolean cancelled = tradeService.cancelOrder(uuid);

      if (cancelled) {
        System.out.println("Order successfully canceled.");
      } else {
        System.out.println("Order not successfully canceled.");
      }

      Thread.sleep(1000); // wait for cancellation to propagate

      System.out.println();
      System.out.println(tradeService.getOpenOrders());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void raw(BiboxTradeServiceRaw tradeService) throws IOException {
    try {
      Integer orderId = tradeService.placeBiboxLimitOrder(LIMIT_ORDER);
      System.out.println("Order successfully placed. ID=" + orderId);

      Thread.sleep(1000); // wait for order to propagate

      System.out.println();
      System.out.println(tradeService.getBiboxOpenOrders());

      System.out.println("Attempting to cancel order " + orderId);
      tradeService.cancelBiboxOrder(orderId.toString());

      Thread.sleep(1000); // wait for cancellation to propagate

      System.out.println();
      System.out.println(tradeService.getBiboxOpenOrders());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
