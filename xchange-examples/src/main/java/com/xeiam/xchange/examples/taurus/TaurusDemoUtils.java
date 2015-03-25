package com.xeiam.xchange.examples.taurus;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.taurus.TaurusExchange;

/**
 * @author Matija Mazi
 */
public class TaurusDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new TaurusExchange().getDefaultExchangeSpecification();
    // set your actual credentials here
    exSpec.setUserName("1234");
    exSpec.setApiKey("AbCdEfGhIj");
    exSpec.setSecretKey("012345689abcdef");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
