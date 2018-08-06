package org.knowm.xchange.examples.coindirect;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coindirect.CoindirectExchange;

public class CoindirectDemoUtils {

  public static Exchange createExchange() {
    Exchange exchange;

    String apiKey = null; /* Set your keys here */
    String apiSecret = null;

    if (apiKey != null && apiSecret != null) {
      exchange =
          ExchangeFactory.INSTANCE.createExchange(
              CoindirectExchange.class.getName(), apiKey, apiSecret);
    } else {
      exchange = ExchangeFactory.INSTANCE.createExchange(CoindirectExchange.class.getName());
    }

    /** substitute this with an exchange with your credentials to test authenticated services */
    return exchange;
  }
}
