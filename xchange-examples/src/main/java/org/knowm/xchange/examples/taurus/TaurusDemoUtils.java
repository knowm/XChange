package org.knowm.xchange.examples.taurus;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.taurus.TaurusExchange;

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
