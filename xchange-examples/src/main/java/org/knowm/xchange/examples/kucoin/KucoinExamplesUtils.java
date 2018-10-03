package org.knowm.xchange.examples.kucoin;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kucoin.KucoinExchange;

public class KucoinExamplesUtils {

  public static Exchange getExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(KucoinExchange.class);
    exSpec.setApiKey("your-api-key");
    exSpec.setSecretKey("your-secret-key");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
