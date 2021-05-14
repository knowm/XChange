package org.knowm.xchange.coinjar;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.AuthUtils;

public class ExchangeUtils {
  public static Exchange createExchangeFromProperties() {
    ExchangeSpecification exSpec = new ExchangeSpecification(CoinjarExchange.class);
    AuthUtils.setApiAndSecretKey(exSpec);
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
