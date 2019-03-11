package org.knowm.xchange.examples.huobi;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.huobi.HuobiExchange;

public class HuobiDemoUtils {
  public static Exchange createExchange() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class.getName());
    return exchange;
  }
}
