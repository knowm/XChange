package org.knowm.xchange.examples.coinbene;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbene.CoinbeneExchange;

public class CoinbeneDemoUtils {

  public static Exchange createExchange() {
    return ExchangeFactory.INSTANCE.createExchange(CoinbeneExchange.class.getName());
  }
}
