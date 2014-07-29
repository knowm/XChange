package com.xeiam.xchange.examples.bittrex.v1;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bittrex.v1.BittrexExchange;

public class BittrexExamplesUtils {

  public static Exchange getExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(BittrexExchange.class);
    exSpec.setApiKey("407495e35a2b4de2823b6eb70f15d767");
    exSpec.setSecretKey("77deef66d6bd4a6e80332dd4dd502f10");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

}
