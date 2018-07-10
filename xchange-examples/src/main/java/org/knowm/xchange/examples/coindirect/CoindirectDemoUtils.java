package org.knowm.xchange.examples.coindirect;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coindirect.CoindirectExchange;

public class CoindirectDemoUtils {

  public static Exchange createExchange() {
    return ExchangeFactory.INSTANCE.createExchange(CoindirectExchange.class.getName());
  }
}
