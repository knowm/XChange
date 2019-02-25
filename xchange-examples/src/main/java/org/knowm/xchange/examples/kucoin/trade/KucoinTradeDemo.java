package org.knowm.xchange.examples.kucoin.trade;

import java.math.BigDecimal;
import java.util.UUID;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.kucoin.KucoinExamplesUtils;
import org.knowm.xchange.kucoin.KucoinTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;

import com.kucoin.sdk.rest.request.OrderCreateApiRequest;
import com.kucoin.sdk.rest.response.OrderCancelResponse;
import com.kucoin.sdk.rest.response.OrderCreateResponse;

public class KucoinTradeDemo {
  private static final CurrencyPair PAIR = new CurrencyPair("ETH", "BTC");
  private static final OrderType ORDER_TYPE = OrderType.ASK;

  public static void main(String[] args) throws Exception {

    Exchange exchange = KucoinExamplesUtils.getExchange();

    TradeService tradeService = exchange.getTradeService();

    raw((KucoinTradeServiceRaw) tradeService);
    System.out.println("");
    generic(tradeService);
  }

  private static void generic(TradeService tradeService) throws Exception {

    System.out.println("GENERIC...\n");

    LimitOrder limitOrder =
        new LimitOrder.Builder(ORDER_TYPE, PAIR)
            .limitPrice(new BigDecimal("3000"))
            .originalAmount(new BigDecimal("1"))
            .build();

    String uuid = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Order successfully placed. ID=" + uuid);

    Thread.sleep(3000); // wait for order to propagate

    System.out.println("All open orders:");
    System.out.println(tradeService.getOpenOrders());

    System.out.println("ETH orders:");
    OpenOrdersParamCurrencyPair orderParams =
        (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    orderParams.setCurrencyPair(PAIR);
    System.out.println(tradeService.getOpenOrders(orderParams));

    System.out.println("All fills: " + tradeService.getTradeHistory(null));
    TradeHistoryParamCurrencyPair tradeHistoryParams =
        (TradeHistoryParamCurrencyPair) tradeService.createTradeHistoryParams();
    tradeHistoryParams.setCurrencyPair(PAIR);
    System.out.println("ETH fills: " + tradeService.getTradeHistory(tradeHistoryParams));

    System.out.println("Attempting to cancel order " + uuid);
    boolean cancelled = tradeService.cancelOrder(uuid);

    if (cancelled) {
      System.out.println("Order successfully canceled.");
    } else {
      System.out.println("Order not successfully canceled.");
    }

    Thread.sleep(3000); // wait for cancellation to propagate

    System.out.println("All open orders:");
    System.out.println(tradeService.getOpenOrders(orderParams));
  }

  private static void raw(KucoinTradeServiceRaw tradeService) throws Exception {

    System.out.println("RAW...\n");

    OrderCreateApiRequest limitOrder = OrderCreateApiRequest.builder()
        .size(new BigDecimal("1"))
        .price(new BigDecimal("3000"))
        .side("sell")
        .symbol("ETH-BTC")
        .type("limit")
        .clientOid(UUID.randomUUID().toString())
        .build();

    OrderCreateResponse response = tradeService.kucoinCreateOrder(limitOrder);
    String orderId = response.getOrderId();
    System.out.println("Order successfully placed. ID=" + orderId);

    Thread.sleep(3000); // wait for order to propagate

    System.out.println("All orders: " + tradeService.getKucoinOpenOrders(null, 1, 1000));
    System.out.println("ETH orders: " + tradeService.getKucoinOpenOrders("ETH-BTC", 1, 1000));
    System.out.println("All fills: " + tradeService.getKucoinFills(null, 0, 1000));
    System.out.println("ETH fills: " + tradeService.getKucoinFills("ETH-BTC", 0, 1000));

    // Not yet implemented
    System.out.println("Attempting to cancel order " + orderId);
    OrderCancelResponse cancelResponse = tradeService.kucoinCancelOrder(orderId);

    if (cancelResponse.getCancelledOrderIds().contains(orderId)) {
      System.out.println("Order successfully canceled.");
    } else {
      System.out.println("Order not successfully canceled.");
    }

    Thread.sleep(3000); // wait for cancellation to propagate

    System.out.println("ETH orders: " + tradeService.getKucoinOpenOrders("ETH-BTC", 1, 1000));
  }
}
