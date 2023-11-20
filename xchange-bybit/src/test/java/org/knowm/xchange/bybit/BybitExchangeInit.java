package org.knowm.xchange.bybit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

public class BybitExchangeInit {

  public static Exchange getBybitExchange() {
    return ExchangeFactory.INSTANCE.createExchange(BybitExchange.class);
  }
}
