package org.knowm.xchange.examples.kucoin.trade;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.examples.kucoin.KucoinExamplesUtils;
import org.knowm.xchange.kucoin.KucoinTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;

public class KucoinTradeDemo {
  private static final CurrencyPair PAIR = new CurrencyPair("DRGN", "BTC");
//  private static final OrderType ORDER_TYPE = OrderType.ASK;

  public static void main(String[] args) throws Exception {

    Exchange exchange = KucoinExamplesUtils.getExchange();

    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((KucoinTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws Exception {

    System.out.println("GENERIC...\n");

    // Not yet implemented
//    LimitOrder limitOrder =
//        new LimitOrder.Builder(ORDER_TYPE, PAIR)
//            .limitPrice(new BigDecimal("100.0"))
//            .originalAmount(new BigDecimal("1"))
//            .build();
//
//    String uuid = tradeService.placeLimitOrder(limitOrder);
//    System.out.println("Order successfully placed. ID=" + uuid);
//
//    Thread.sleep(7000); // wait for order to propagate
//
//    System.out.println();

    System.out.println("All orders:");
    System.out.println(tradeService.getOpenOrders());

    System.out.println("DRGN orders:");
    OpenOrdersParamCurrencyPair orderParams =
        (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    orderParams.setCurrencyPair(PAIR);
    System.out.println(tradeService.getOpenOrders(orderParams));

    System.out.println("All fills: " + tradeService.getTradeHistory(null));
    TradeHistoryParamCurrencyPair tradeHistoryParams =
        (TradeHistoryParamCurrencyPair) tradeService.createTradeHistoryParams();
    tradeHistoryParams.setCurrencyPair(PAIR);
    System.out.println("DRGN fills: " + tradeService.getTradeHistory(tradeHistoryParams));

    // Not yet implemented
//    System.out.println("Attempting to cancel order " + uuid);
//    CancelOrderParams cancelParams = new KucoinCancelOrderParams(PAIR, uuid, ORDER_TYPE);
//    boolean cancelled = tradeService.cancelOrder(cancelParams);
//
//    if (cancelled) {
//      System.out.println("Order successfully canceled.");
//    } else {
//      System.out.println("Order not successfully canceled.");
//    }
//
//    Thread.sleep(7000); // wait for cancellation to propagate
//
//    System.out.println();
//    System.out.println(tradeService.getOpenOrders(orderParams));
  }

  private static void raw(KucoinTradeServiceRaw tradeService) throws Exception {

    System.out.println("RAW...\n");

    // Not yet implemented
//    LimitOrder limitOrder =
//        new LimitOrder.Builder(ORDER_TYPE, PAIR)
//            .limitPrice(new BigDecimal("100"))
//            .originalAmount(new BigDecimal("1"))
//            .build();
//
//    KucoinResponse<KucoinOrder> limitOrderResponse =
//        tradeService.placeKucoinLimitOrder(limitOrder);
//    String orderId = limitOrderResponse.getData().getOrderOid();
//    System.out.println("Order successfully placed. ID=" + orderId);
//
//    Thread.sleep(7000); // wait for order to propagate
//
//    System.out.println();
    System.out.println("All orders: " + tradeService.getKucoinOpenOrders(null, 1, 1000));
    System.out.println("DRGN orders: " + tradeService.getKucoinOpenOrders("DRGN-BTC", 1, 1000));
    System.out.println("All fills: " + tradeService.getKucoinFills(null, 0, 1000));
    System.out.println("DRGN fills: " + tradeService.getKucoinFills("DRGN-BTC", 0, 1000));

    // Not yet implemented
//    System.out.println("Attempting to cancel order " + orderId);
//    KucoinResponse<KucoinOrder> cancelResponse =
//        tradeService.cancelKucoinOrder(PAIR, orderId, ORDER_TYPE);
//
//    if (cancelResponse.isSuccess()) {
//      System.out.println("Order successfully canceled.");
//    } else {
//      System.out.println("Order not successfully canceled.");
//    }
//
//    Thread.sleep(7000); // wait for cancellation to propagate
//
//    System.out.println();
//    System.out.println(tradeService.getKucoinOpenOrders(PAIR, null));
  }
}
