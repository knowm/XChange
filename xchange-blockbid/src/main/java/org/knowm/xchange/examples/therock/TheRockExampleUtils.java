package org.knowm.xchange.examples.therock;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.therock.TheRockExchange;

public class TheRockExampleUtils {

  private TheRockExampleUtils() {}

  public static Exchange createTestExchange() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(TheRockExchange.class.getName());
    exchange.getExchangeSpecification().setApiKey("API Key");
    exchange.getExchangeSpecification().setSecretKey("Secret==");
    exchange.getExchangeSpecification().setUserName("user");
    exchange.applySpecification(exchange.getExchangeSpecification());
    return exchange;
  }
}
