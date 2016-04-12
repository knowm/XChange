package org.knowm.xchange.examples.loyalbit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.loyalbit.LoyalbitExchange;

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
