package org.knowm.xchange.examples.bittrex;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bittrex.BittrexExchange;

public class BittrexExamplesUtils {

  public static Exchange getExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(BittrexExchange.class);
    exSpec.setApiKey("407495e35a2b4de2823b6eb70f15d767");
    exSpec.setSecretKey("77deef66d6bd4a6e80332dd4dd502f10");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
