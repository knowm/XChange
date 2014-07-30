package com.xeiam.xchange.examples.anx.v2;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.anx.v2.ANXExchange;

/**
 * @author timmolter
 */
public class ANXExamplesUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(ANXExchange.class);

    exSpec.setApiKey("");
    exSpec.setSecretKey("");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
