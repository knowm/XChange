package org.knowm.xchange.coinfloor.service;

import java.io.IOException;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinfloor.CoinfloorExchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinfloorCancelOrderIntegration {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Test
  public void cancelOpenOrdersTest() throws IOException {
    final ExchangeSpecification specification = new ExchangeSpecification(CoinfloorExchange.class);

    String username = System.getProperty("xchange.coinfloor.username");
    String password = System.getProperty("xchange.coinfloor.password");

    if (username == null || password == null) {
      return;
    }

    specification.setUserName(username);
    specification.setPassword(password);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(specification);
    TradeService service = exchange.getTradeService();

    // fetch open orders
    CoinfloorOpenOrdersParams params = (CoinfloorOpenOrdersParams) service.createOpenOrdersParams();
    OpenOrders openOrders = service.getOpenOrders(params);

    // cancel one order
    if (openOrders.getOpenOrders().size() > 0) {
      LimitOrder order = openOrders.getOpenOrders().iterator().next();
      boolean success = service.cancelOrder(order.getId());
      logger.info("cancel of order={} success={}", order, success);
    }
  }
}
