package org.knowm.xchange.examples.cobinhood;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cobinhood.CobinhoodExchange;

public class CobinhoodDemoUtils {

  public static Exchange createExchange() {
    return ExchangeFactory.INSTANCE.createExchange(CobinhoodExchange.class.getName());
  }
}
