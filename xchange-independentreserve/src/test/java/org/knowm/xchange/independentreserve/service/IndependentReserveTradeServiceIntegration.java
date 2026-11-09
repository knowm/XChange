package org.knowm.xchange.independentreserve.service;

import java.util.Collection;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.independentreserve.IndependentReserveExchange;
import org.knowm.xchange.utils.AuthUtils;

class IndependentReserveTradeServiceIntegration {

  static Exchange exchange;
  static IndependentReserveTradeService tradeService;

  @BeforeAll
  static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(IndependentReserveExchange.class);
    AuthUtils.setApiAndSecretKey(exchange.getExchangeSpecification());
    exchange = ExchangeFactory.INSTANCE.createExchange(exchange.getExchangeSpecification());
    tradeService = (IndependentReserveTradeService) exchange.getTradeService();
  }

  @Test
  void getOpenOrders() throws Exception {

    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);

    OpenOrders openOrders = tradeService.getOpenOrders();
  }

  @Test
  void getTradeHistory() throws Exception {
    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);

    UserTrades userTrades = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    if (userTrades.getUserTrades().size() > 0) {
      UserTrade userTrade = userTrades.getUserTrades().get(0);
      String orderId = userTrade.getOrderId();
      Collection<Order> orders = tradeService.getOrder(orderId);
    }
  }
}
