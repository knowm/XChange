package org.knowm.xchange.therock.service.trade;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.therock.TheRockExchange;

public abstract class AbstractTheRockTradeServiceIntegrationTest {

  /**
   * Substitute apiKey, secretKey and userName in order to run the inherited integration tests
   *
   * @return an instance of class TheRockExchange
   */
  protected static Exchange createExchange() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(TheRockExchange.class.getName());
    exchange.getExchangeSpecification().setApiKey("ApiKey");
    exchange.getExchangeSpecification().setSecretKey("SecretKey");
    exchange.getExchangeSpecification().setUserName("UserName");
    exchange.applySpecification(exchange.getExchangeSpecification());
    return exchange;
  }

}
