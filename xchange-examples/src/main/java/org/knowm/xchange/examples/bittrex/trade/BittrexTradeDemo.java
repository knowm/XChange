package org.knowm.xchange.examples.bittrex.trade;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.service.BittrexTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.bittrex.BittrexExamplesUtils;
import org.knowm.xchange.service.trade.TradeService;

public class BittrexTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BittrexExamplesUtils.getExchange();

    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((BittrexTradeServiceRaw) tradeService);

  }

  private static void generic(TradeService tradeService) throws IOException {

    CurrencyPair pair = new CurrencyPair("ZET", "BTC");
    LimitOrder limitOrder = new LimitOrder.Builder(OrderType.BID, pair).limitPrice(new BigDecimal("0.00001000")).tradableAmount(new BigDecimal("100"))
        .build();

    try {
      String uuid = tradeService.placeLimitOrder(limitOrder);
      System.out.println("Order successfully placed. ID=" + uuid);

      Thread.sleep(7000); // wait for order to propagate

      System.out.println();
      System.out.println(tradeService.getOpenOrders());

      System.out.println("Attempting to cancel order " + uuid);
      boolean cancelled = tradeService.cancelOrder(uuid);

      if (cancelled) {
        System.out.println("Order successfully canceled.");
      } else {
        System.out.println("Order not successfully canceled.");
      }

      Thread.sleep(7000); // wait for cancellation to propagate

      System.out.println();
      System.out.println(tradeService.getOpenOrders());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void raw(BittrexTradeServiceRaw tradeService) throws IOException {

    CurrencyPair pair = new CurrencyPair("ZET", "BTC");
    LimitOrder limitOrder = new LimitOrder.Builder(OrderType.BID, pair).limitPrice(new BigDecimal("0.00001000")).tradableAmount(new BigDecimal("100"))
        .build();

    try {
      String uuid = tradeService.placeBittrexLimitOrder(limitOrder);
      System.out.println("Order successfully placed. ID=" + uuid);

      Thread.sleep(7000); // wait for order to propagate

      System.out.println();
      System.out.println(tradeService.getBittrexOpenOrders(null));

      System.out.println("Attempting to cancel order " + uuid);
      boolean cancelled = tradeService.cancelBittrexLimitOrder(uuid);

      if (cancelled) {
        System.out.println("Order successfully canceled.");
      } else {
        System.out.println("Order not successfully canceled.");
      }

      Thread.sleep(7000); // wait for cancellation to propagate

      System.out.println();
      System.out.println(tradeService.getBittrexOpenOrders(null));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
