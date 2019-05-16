package org.knowm.xchange.examples.quoine;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.quoine.QuoineExchange;

/** @author timmolter */
public class QuoineExamplesUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(QuoineExchange.class);

    // enter your specific API access info here
    exSpec.setApiKey("KEY_TOKEN_ID");
    exSpec.setSecretKey("KEY_USER_SECRET");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
