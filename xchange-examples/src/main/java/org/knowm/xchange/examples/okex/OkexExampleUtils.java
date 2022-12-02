package org.knowm.xchange.examples.okex;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.okex.OkexExchange;

public class OkexExampleUtils {

  private OkexExampleUtils() {}

  public static Exchange createTestExchange() {

    Exchange okcoinExchange = ExchangeFactory.INSTANCE.createExchange(OkexExchange.class);
    okcoinExchange.getExchangeSpecification().setApiKey("");
    okcoinExchange.getExchangeSpecification().setSecretKey("");
    okcoinExchange.getExchangeSpecification().setUserName("");
    okcoinExchange.applySpecification(okcoinExchange.getExchangeSpecification());
    return okcoinExchange;
  }
}
