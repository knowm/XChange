package org.knowm.xchange.examples.okcoin.v1;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.okcoin.v1.OkCoinExchange;

public class OkCoinExampleUtils {

  private OkCoinExampleUtils() {

  }

  public static Exchange createTestExchange() {

    Exchange okcoinExchange = ExchangeFactory.INSTANCE.createExchange(OkCoinExchange.class.getName());
    okcoinExchange.getExchangeSpecification().setApiKey("");
    okcoinExchange.getExchangeSpecification().setSecretKey("");
    okcoinExchange.getExchangeSpecification().setUserName("");
    okcoinExchange.applySpecification(okcoinExchange.getExchangeSpecification());
    return okcoinExchange;
  }
}
