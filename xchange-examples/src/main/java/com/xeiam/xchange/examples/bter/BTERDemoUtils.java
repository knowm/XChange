package com.xeiam.xchange.examples.bter;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bter.BTERExchange;

public class BTERDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new BTERExchange().getDefaultExchangeSpecification();
    exSpec.setApiKey("");
    exSpec.setSecretKey("");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
