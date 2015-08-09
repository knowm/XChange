package com.xeiam.xchange.examples.therock;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.therock.TheRockExchange;

public class TheRockExampleUtils {

  private TheRockExampleUtils() {
  }

  public static Exchange createTestExchange() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(TheRockExchange.class.getName());
    exchange.getExchangeSpecification().setApiKey("API Key");
    exchange.getExchangeSpecification().setSecretKey("Secret==");
    exchange.getExchangeSpecification().setUserName("user");
    exchange.applySpecification(exchange.getExchangeSpecification());
    return exchange;
  }
}
