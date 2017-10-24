package org.knowm.xchange.coinfloor.service;

import java.io.IOException;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinfloor.CoinfloorExchange;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinfloorUserTradesIntegration {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Test
  public void fetchUserTradesTest() throws IOException {
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
    CoinfloorTradeHistoryParams params = (CoinfloorTradeHistoryParams) service.createTradeHistoryParams();
    UserTrades trades = service.getTradeHistory(params);
    logger.info("{}", trades);
  }
}
