package org.knowm.xchange.examples.coinbase.v2;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbase.v2.CoinbaseExchange;
import org.knowm.xchange.utils.AuthUtils;

public class CoinbaseDemoUtils {

  public static Exchange createExchange() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class);
    AuthUtils.setApiAndSecretKey(exchange.getExchangeSpecification(), "coinbase");
    return exchange;
  }
}
