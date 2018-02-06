package org.knowm.xchange.examples.kucoin.trade;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.kucoin.KucoinExamplesUtils;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.trading.KucoinOrder;
import org.knowm.xchange.kucoin.service.KucoinCancelOrderParams;
import org.knowm.xchange.kucoin.service.KucoinTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;

public class KucoinTradeDemo {

  private static final CurrencyPair PAIR = new CurrencyPair("DRGN", "BTC");
  private static final OrderType ORDER_TYPE = OrderType.ASK;

  public static void main(String[] args) throws IOException {

    Exchange exchange = KucoinExamplesUtils.getExchange();

    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((KucoinTradeServiceRaw) tradeService);

  }

  private static void generic(TradeService tradeService) throws IOException {

    LimitOrder limitOrder = new LimitOrder.Builder(ORDER_TYPE, PAIR).limitPrice(new BigDecimal("100.0")).originalAmount(new BigDecimal("1"))
        .build();

    try {
      String uuid = tradeService.placeLimitOrder(limitOrder);
      System.out.println("Order successfully placed. ID=" + uuid);

      Thread.sleep(7000); // wait for order to propagate

      System.out.println();
      DefaultOpenOrdersParamCurrencyPair orderParams = (DefaultOpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
      orderParams.setCurrencyPair(PAIR);
      System.out.println(tradeService.getOpenOrders(orderParams));

      System.out.println("Attempting to cancel order " + uuid);
      CancelOrderParams cancelParams = new KucoinCancelOrderParams(PAIR, uuid, ORDER_TYPE);
      boolean cancelled = tradeService.cancelOrder(cancelParams);

      if (cancelled) {
        System.out.println("Order successfully canceled.");
      } else {
        System.out.println("Order not successfully canceled.");
      }

      Thread.sleep(7000); // wait for cancellation to propagate

      System.out.println();
      System.out.println(tradeService.getOpenOrders(orderParams));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void raw(KucoinTradeServiceRaw tradeService) throws IOException {

    LimitOrder limitOrder = new LimitOrder.Builder(ORDER_TYPE, PAIR).limitPrice(new BigDecimal("100")).originalAmount(new BigDecimal("1"))
        .build();

    try {
      KucoinResponse<KucoinOrder> limitOrderResponse = tradeService.placeKucoinLimitOrder(limitOrder);
      String orderId = limitOrderResponse.getData().getOrderOid();
      System.out.println("Order successfully placed. ID=" + orderId);

      Thread.sleep(7000); // wait for order to propagate

      System.out.println();
      System.out.println(tradeService.getKucoinOpenOrders(PAIR, null));

      System.out.println("Attempting to cancel order " + orderId);
      KucoinResponse<KucoinOrder> cancelResponse = tradeService.cancelKucoinOrder(PAIR, orderId, ORDER_TYPE);

      if (cancelResponse.isSuccess()) {
        System.out.println("Order successfully canceled.");
      } else {
        System.out.println("Order not successfully canceled.");
      }

      Thread.sleep(7000); // wait for cancellation to propagate

      System.out.println();
      System.out.println(tradeService.getKucoinOpenOrders(PAIR, null));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
