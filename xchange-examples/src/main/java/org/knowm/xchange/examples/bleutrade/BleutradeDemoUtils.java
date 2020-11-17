package org.knowm.xchange.examples.bleutrade;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bleutrade.BleutradeExchange;

public class BleutradeDemoUtils {

  public static Exchange getExchange() {

    Exchange bleutrade = ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class);
    ExchangeSpecification exchangeSpecification = bleutrade.getDefaultExchangeSpecification();
    exchangeSpecification.setApiKey("");
    exchangeSpecification.setSecretKey("");
    bleutrade.applySpecification(exchangeSpecification);

    return bleutrade;
  }
}
