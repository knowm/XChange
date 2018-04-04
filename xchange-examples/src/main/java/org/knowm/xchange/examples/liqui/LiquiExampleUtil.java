package org.knowm.xchange.examples.liqui;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.liqui.LiquiExchange;

public class LiquiExampleUtil {

  public static final String KEY = "";
  public static final String SECRET = "";

  public static Exchange createTestExchange() {
    final Exchange liquiExchange =
        ExchangeFactory.INSTANCE.createExchange(LiquiExchange.class.getName());
    liquiExchange.getExchangeSpecification().setApiKey(KEY);
    liquiExchange.getExchangeSpecification().setSecretKey(SECRET);
    liquiExchange.applySpecification(liquiExchange.getExchangeSpecification());

    return liquiExchange;
  }
}
