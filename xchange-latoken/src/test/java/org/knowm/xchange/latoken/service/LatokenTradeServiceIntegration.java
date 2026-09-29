package org.knowm.xchange.latoken.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.latoken.LatokenExchange;
import org.knowm.xchange.latoken.dto.trade.LatokenOrderSide;
import org.knowm.xchange.latoken.dto.trade.LatokenTestOrder;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LatokenTradeServiceIntegration {

  static Logger LOG = LoggerFactory.getLogger(LatokenTradeServiceIntegration.class);

  static Exchange exchange;
  static LatokenTradeService tradeService;

  @BeforeAll
  static void beforeClass() {
    exchange =
        ExchangeFactory.INSTANCE.createExchange(
            LatokenExchange.class, "api-v1-XXX", "api-v1-secret-YYY");
    tradeService = (LatokenTradeService) exchange.getTradeService();
  }

  @BeforeEach
  void before() {
    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);
  }

  @Test
  void openOrders() throws Exception {

    DefaultOpenOrdersParamCurrencyPair params =
        (DefaultOpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    params.setCurrencyPair(CurrencyPair.ETH_BTC);
    List<LimitOrder> orders = tradeService.getOpenOrders(params).getOpenOrders();
    orders.forEach(order -> System.out.println(order));
  }

  @Test
  void newOrder() throws Exception {

    CurrencyPair pair = CurrencyPair.ETH_BTC;
    OrderType type = OrderType.BID;
    BigDecimal amount = BigDecimal.valueOf(0.01);
    BigDecimal limitPrice = BigDecimal.valueOf(0.018881);
    LimitOrder newOrder =
        new LimitOrder.Builder(type, pair)
            .originalAmount(amount)
            .limitPrice(limitPrice)
            .timestamp(new Date(System.currentTimeMillis()))
            .build();

    // Test order
    LatokenTestOrder testOrder =
        tradeService.placeLatokenTestOrder(pair, "", LatokenOrderSide.buy, limitPrice, amount);
    System.out.println(testOrder);

    // Place order
    String newOrderId = tradeService.placeLimitOrder(newOrder);
    System.out.println(newOrderId);

    // Check open orders
    DefaultOpenOrdersParamCurrencyPair params =
        (DefaultOpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    params.setCurrencyPair(CurrencyPair.ETH_BTC);
    List<LimitOrder> openOrders = tradeService.getOpenOrders(params).getOpenOrders();
    System.out.println(openOrders);

    // Cancel
    tradeService.cancelLatokenOrder(newOrderId);

    // Check open orders
    openOrders = tradeService.getOpenOrders().getOpenOrders();
    System.out.println(openOrders);
  }
}
