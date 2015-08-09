package com.xeiam.xchange.examples.loyalbit;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.loyalbit.LoyalbitExchange;

public class LoyalbitExampleUtils {

  private LoyalbitExampleUtils() {
  }

  public static Exchange createTestExchange() {
    Exchange loyalbitExchange = ExchangeFactory.INSTANCE.createExchange(LoyalbitExchange.class.getName());
    loyalbitExchange.getExchangeSpecification().setApiKey("API Key");
    loyalbitExchange.getExchangeSpecification().setSecretKey("Secret==");
    loyalbitExchange.getExchangeSpecification().setUserName("user");
    loyalbitExchange.applySpecification(loyalbitExchange.getExchangeSpecification());
    return loyalbitExchange;
  }
}
