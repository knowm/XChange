package org.knowm.xchange.coinfloor.service;

import java.io.IOException;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinfloor.CoinfloorExchange;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinfloorOpenOrdersIntegration {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Test
  public void fetchOpenOrdersTest() throws IOException {
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
    CoinfloorOpenOrdersParams params = (CoinfloorOpenOrdersParams) service.createOpenOrdersParams();
    OpenOrders openOrders = service.getOpenOrders(params);
    logger.info("{}", openOrders);
  }
}
