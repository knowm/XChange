package com.xeiam.xchange.examples.bleutrade;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bleutrade.BleutradeExchange;

public class BleutradeDemoUtils {

  public static Exchange getExchange() {

    Exchange bleutrade = ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getName());
    ExchangeSpecification exchangeSpecification = bleutrade.getDefaultExchangeSpecification();
    exchangeSpecification.setApiKey("");
    exchangeSpecification.setSecretKey("");
    bleutrade.applySpecification(exchangeSpecification);

    return bleutrade;
  }
}
