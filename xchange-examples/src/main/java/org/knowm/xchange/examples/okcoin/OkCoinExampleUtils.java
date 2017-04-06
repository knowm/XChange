package org.knowm.xchange.examples.okcoin;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.okcoin.OkCoinExchange;

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
