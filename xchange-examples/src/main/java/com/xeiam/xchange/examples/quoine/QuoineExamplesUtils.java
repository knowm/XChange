package com.xeiam.xchange.examples.quoine;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.quoine.QuoineExchange;

/**
 * @author timmolter
 */
public class QuoineExamplesUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(QuoineExchange.class);

    // enter your specific API access info here
    exSpec.getExchangeSpecificParameters().put(QuoineExchange.KEY_USER_ID, "");
    exSpec.getExchangeSpecificParameters().put(QuoineExchange.KEY_DEVICE_NAME, "");
    exSpec.getExchangeSpecificParameters().put(QuoineExchange.KEY_USER_TOKEN, "");

    exSpec.setSecretKey("");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
