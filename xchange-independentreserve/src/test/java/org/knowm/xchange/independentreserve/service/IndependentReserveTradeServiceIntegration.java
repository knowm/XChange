package org.knowm.xchange.independentreserve.service;

import java.util.Collection;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.independentreserve.IndependentReserveExchange;
import org.knowm.xchange.utils.AuthUtils;

public class IndependentReserveTradeServiceIntegration {

  static Exchange exchange;
  static IndependentReserveTradeService tradeService;

  @BeforeClass
  public static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(IndependentReserveExchange.class);
    AuthUtils.setApiAndSecretKey(exchange.getExchangeSpecification());
    exchange = ExchangeFactory.INSTANCE.createExchange(exchange.getExchangeSpecification());
    tradeService = (IndependentReserveTradeService) exchange.getTradeService();
  }

  @Test
  public void testGetOpenOrders() throws Exception {

    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());

    OpenOrders openOrders = tradeService.getOpenOrders();
  }

  @Test
  public void testGetTradeHistory() throws Exception {
    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());

    UserTrades userTrades = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    if (userTrades.getUserTrades().size() > 0) {
      UserTrade userTrade = userTrades.getUserTrades().get(0);
      String orderId = userTrade.getOrderId();
      Collection<Order> orders = tradeService.getOrder(orderId);
    }
  }
}
