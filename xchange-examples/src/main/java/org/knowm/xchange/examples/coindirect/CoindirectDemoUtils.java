package org.knowm.xchange.examples.coindirect;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coindirect.CoindirectExchange;

public class CoindirectDemoUtils {

  public static Exchange createExchange() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoindirectExchange.class.getName());

    /**
     * substitute this with an exchange with your credentials to test authenticated services
     */

    return exchange;
  }
}
