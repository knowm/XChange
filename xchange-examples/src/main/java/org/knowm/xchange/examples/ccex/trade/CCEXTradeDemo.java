package org.knowm.xchange.examples.ccex.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ccex.service.CCEXTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.ccex.CCEXExamplesUtils;
import org.knowm.xchange.service.trade.TradeService;

public class CCEXTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CCEXExamplesUtils.getExchange();

    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((CCEXTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    CurrencyPair pair = new CurrencyPair("DASH", "BTC");
    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.BID, pair)
            .limitPrice(new BigDecimal("0.00001000"))
            .originalAmount(new BigDecimal("100"))
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

  private static void raw(CCEXTradeServiceRaw tradeService) throws IOException {

    CurrencyPair pair = new CurrencyPair("DASH", "BTC");
    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.BID, pair)
            .limitPrice(new BigDecimal("0.00001000"))
            .originalAmount(new BigDecimal("100"))
            .build();

    try {
      String uuid = tradeService.placeCCEXLimitOrder(limitOrder);
      System.out.println("Order successfully placed. ID=" + uuid);

      Thread.sleep(7000); // wait for order to propagate

      System.out.println();
      System.out.println(tradeService.getCCEXOpenOrders());

      System.out.println("Attempting to cancel order " + uuid);
      boolean cancelled = tradeService.cancelCCEXLimitOrder(uuid);

      if (cancelled) {
        System.out.println("Order successfully canceled.");
      } else {
        System.out.println("Order not successfully canceled.");
      }

      Thread.sleep(7000); // wait for cancellation to propagate

      System.out.println();
      System.out.println(tradeService.getCCEXOpenOrders());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
