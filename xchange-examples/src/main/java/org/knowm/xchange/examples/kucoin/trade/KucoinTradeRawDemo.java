package org.knowm.xchange.examples.kucoin.trade;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.examples.kucoin.KucoinExamplesUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kucoin.KucoinTradeServiceRaw;
import org.knowm.xchange.kucoin.dto.request.OrderCreateApiRequest;
import org.knowm.xchange.kucoin.dto.response.OrderCancelResponse;
import org.knowm.xchange.kucoin.dto.response.OrderCreateResponse;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;

public class KucoinTradeRawDemo {

  private static final CurrencyPair PAIR = new CurrencyPair("ETH", "BTC");
  private static final String SYMBOL = "ETH-BTC";
  private static final OrderType ORDER_TYPE = OrderType.ASK;
  private static final BigDecimal AMOUNT = new BigDecimal("0.1");
  private static final BigDecimal PRICE = new BigDecimal("0.05");

  private static final BigDecimal STOP_PRICE = new BigDecimal("0.01");
  private static final BigDecimal STOP_LIMIT = new BigDecimal("0.005");

  public static void main(String[] args) throws Exception {

    Exchange exchange = KucoinExamplesUtils.getExchange();

    TradeService tradeService = exchange.getTradeService();

    raw((KucoinTradeServiceRaw) tradeService);
    System.out.println("");
    genericLimitOrder(tradeService);
    genericStopLimitOrder(tradeService);
    genericStopMarketOrder(tradeService);
  }

  private static void genericLimitOrder(TradeService tradeService) throws Exception {

    System.out.println("GENERIC LIMIT ORDER...\n");

    LimitOrder limitOrder =
        new LimitOrder.Builder(ORDER_TYPE, PAIR).limitPrice(PRICE).originalAmount(AMOUNT).build();

    String uuid = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Order successfully placed. ID=" + uuid);

    Thread.sleep(3000); // wait for order to propagate

    System.out.println(tradeService.getOpenOrders());

    System.out.println(SYMBOL + " orders:");
    OpenOrdersParamCurrencyPair orderParams =
        (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    orderParams.setCurrencyPair(PAIR);
    OpenOrders openOrders = tradeService.getOpenOrders(orderParams);
    System.out.println(openOrders);

    Optional<LimitOrder> found =
        openOrders.getOpenOrders().stream().filter(o -> o.getId().equals(uuid)).findFirst();
    if (!found.isPresent()) {
      throw new AssertionError("Order not found on book");
    }
    if (found.get().getLimitPrice().compareTo(PRICE) != 0) {
      throw new AssertionError("Limit price mismatch: " + found.get().getLimitPrice());
    }
    if (found.get().getOriginalAmount().compareTo(AMOUNT) != 0) {
      throw new AssertionError("Amount mismatch: " + found.get().getOriginalAmount());
    }

    System.out.println("All fills: " + tradeService.getTradeHistory(null));
    TradeHistoryParamCurrencyPair tradeHistoryParams =
        (TradeHistoryParamCurrencyPair) tradeService.createTradeHistoryParams();
    tradeHistoryParams.setCurrencyPair(PAIR);
    System.out.println(SYMBOL + " fills: " + tradeService.getTradeHistory(tradeHistoryParams));

    System.out.println("Attempting to cancel order " + uuid);
    boolean cancelled = tradeService.cancelOrder(uuid);

    if (cancelled) {
      System.out.println("Order successfully canceled.");
    } else {
      System.out.println("Order not successfully canceled.");
    }

    try {
      if (tradeService.cancelOrder("NONEXISTENT")) {
        throw new AssertionError("Shouldn't be able to cancel a non-existent order");
      }
    } catch (ExchangeException e) {
      // Fine
    }

    Thread.sleep(3000); // wait for cancellation to propagate

    openOrders = tradeService.getOpenOrders(orderParams);
    System.out.println(tradeService.getOpenOrders(orderParams));

    if (openOrders.getOpenOrders().stream().map(LimitOrder::getId).anyMatch(uuid::equals)) {
      throw new AssertionError("Order should have been found on book");
    }
  }

  private static void genericStopLimitOrder(TradeService tradeService) throws Exception {

    System.out.println("GENERIC STOP LIMIT ORDER...\n");

    StopOrder stopOrder =
        new StopOrder.Builder(ORDER_TYPE, PAIR)
            .limitPrice(STOP_LIMIT)
            .stopPrice(STOP_PRICE)
            .originalAmount(AMOUNT)
            .build();

    String uuid = tradeService.placeStopOrder(stopOrder);
    System.out.println("Stop order successfully placed. ID=" + uuid);

    Thread.sleep(3000); // wait for order to propagate

    OpenOrdersParamCurrencyPair orderParams =
        (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    orderParams.setCurrencyPair(PAIR);
    OpenOrders openOrders = tradeService.getOpenOrders(orderParams);
    Optional<? extends Order> found =
        openOrders.getHiddenOrders().stream().filter(o -> o.getId().equals(uuid)).findFirst();
    if (!found.isPresent()) {
      throw new AssertionError("Order not found on book");
    }
    if (!(found.get() instanceof StopOrder)) {
      throw new AssertionError("Stop order not returned");
    }
    StopOrder returnedStopOrder = (StopOrder) found.get();
    if (returnedStopOrder.getLimitPrice().compareTo(STOP_LIMIT) != 0) {
      throw new AssertionError("Limit price mismatch: " + returnedStopOrder.getLimitPrice());
    }
    if (returnedStopOrder.getStopPrice().compareTo(STOP_PRICE) != 0) {
      throw new AssertionError("Stop price mismatch: " + returnedStopOrder.getStopPrice());
    }

    System.out.println("Attempting to cancel order " + uuid);
    boolean cancelled = tradeService.cancelOrder(uuid);

    if (cancelled) {
      System.out.println("Order successfully canceled.");
    } else {
      System.out.println("Order not successfully canceled.");
    }
  }

  private static void genericStopMarketOrder(TradeService tradeService) throws Exception {

    System.out.println("GENERIC STOP MARKET ORDER...\n");

    StopOrder stopOrder =
        new StopOrder.Builder(ORDER_TYPE, PAIR)
            .stopPrice(STOP_PRICE)
            .originalAmount(AMOUNT)
            .build();

    String uuid = tradeService.placeStopOrder(stopOrder);
    System.out.println("Stop order successfully placed. ID=" + uuid);

    Thread.sleep(3000); // wait for order to propagate

    System.out.println(tradeService.getOpenOrders());

    OpenOrdersParamCurrencyPair orderParams =
        (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    orderParams.setCurrencyPair(PAIR);
    OpenOrders openOrders = tradeService.getOpenOrders(orderParams);
    Optional<? extends Order> found =
        openOrders.getHiddenOrders().stream().filter(o -> o.getId().equals(uuid)).findFirst();
    if (!found.isPresent()) {
      throw new AssertionError("Order not found on book");
    }
    if (!(found.get() instanceof StopOrder)) {
      throw new AssertionError("Stop order not returned");
    }
    StopOrder returnedStopOrder = (StopOrder) found.get();
    if (returnedStopOrder.getLimitPrice() != null) {
      throw new AssertionError("Limit price mismatch: " + returnedStopOrder.getLimitPrice());
    }
    if (returnedStopOrder.getStopPrice().compareTo(STOP_PRICE) != 0) {
      throw new AssertionError("Stop price mismatch: " + returnedStopOrder.getStopPrice());
    }

    System.out.println("Attempting to cancel order " + uuid);
    boolean cancelled = tradeService.cancelOrder(uuid);

    if (cancelled) {
      System.out.println("Order successfully canceled.");
    } else {
      System.out.println("Order not successfully canceled.");
    }
  }

  private static void raw(KucoinTradeServiceRaw tradeService) throws Exception {

    System.out.println("RAW...\n");

    OrderCreateApiRequest limitOrder =
        OrderCreateApiRequest.builder()
            .size(AMOUNT)
            .price(PRICE)
            .side("sell")
            .symbol(SYMBOL)
            .type("limit")
            .clientOid(UUID.randomUUID().toString())
            .build();

    OrderCreateResponse response = tradeService.kucoinCreateOrder(limitOrder);
    String orderId = response.getOrderId();
    System.out.println("Order successfully placed. ID=" + orderId);

    Thread.sleep(3000); // wait for order to propagate

    System.out.println("All orders: " + tradeService.getKucoinOpenOrders(null, 1, 500));
    System.out.println(SYMBOL + " orders: " + tradeService.getKucoinOpenOrders(SYMBOL, 1, 500));
    System.out.println("All fills: " + tradeService.getKucoinFills(null, 1, 500, null, null));
    System.out.println(
        SYMBOL + " fills: " + tradeService.getKucoinFills(SYMBOL, 1, 500, null, null));

    // Not yet implemented
    System.out.println("Attempting to cancel order " + orderId);
    OrderCancelResponse cancelResponse = tradeService.kucoinCancelOrder(orderId);

    if (cancelResponse.getCancelledOrderIds().contains(orderId)) {
      System.out.println("Order successfully canceled.");
    } else {
      System.out.println("Order not successfully canceled.");
    }

    Thread.sleep(3000); // wait for cancellation to propagate

    System.out.println(SYMBOL + " orders: " + tradeService.getKucoinOpenOrders(SYMBOL, 1, 500));
  }
}
