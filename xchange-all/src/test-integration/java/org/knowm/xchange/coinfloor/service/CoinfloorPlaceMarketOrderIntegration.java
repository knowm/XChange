package org.knowm.xchange.coinfloor.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinfloor.CoinfloorExchange;
import org.knowm.xchange.coinfloor.dto.trade.CoinfloorMarketOrderResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinfloorPlaceMarketOrderIntegration {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Test
  public void placeMarketOrderTest() throws IOException {
    final ExchangeSpecification specification = new ExchangeSpecification(CoinfloorExchange.class);

    String username = System.getProperty("xchange.coinfloor.username");
    String password = System.getProperty("xchange.coinfloor.password");

    if (username == null || password == null) {
      return;
    }

    specification.setUserName(username);
    specification.setPassword(password);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(specification);

    CoinfloorTradeServiceRaw rawService = (CoinfloorTradeServiceRaw) exchange.getTradeService();
    CoinfloorMarketOrderResponse order = rawService.placeMarketOrder(CurrencyPair.BTC_EUR, OrderType.ASK, new BigDecimal("0.0001"));
    logger.info("placed market order with remainingQty={}", order.getRemaining());
  }
}
