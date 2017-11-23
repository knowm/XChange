package org.knowm.xchange.examples.ccex;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.ccex.CCEXExchange;

public class CCEXExamplesUtils {
  public static Exchange getExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(CCEXExchange.class);
    exSpec.setApiKey("");
    exSpec.setSecretKey("");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
