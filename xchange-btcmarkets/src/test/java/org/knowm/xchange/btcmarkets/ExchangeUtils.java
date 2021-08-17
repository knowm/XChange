package org.knowm.xchange.btcmarkets;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.AuthUtils;

public class ExchangeUtils {
  public static Exchange createExchangeFromProperties() {
    ExchangeSpecification exSpec = new ExchangeSpecification(BTCMarketsExchange.class);
    AuthUtils.setApiAndSecretKey(exSpec);
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
