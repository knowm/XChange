package org.knowm.xchange.acx;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.AuthUtils;

/** @author by kfonal */
public class ExchangeUtils {
  public static Exchange createExchangeFromProperties() {
    ExchangeSpecification exSpec = new ExchangeSpecification(AcxExchange.class);
    AuthUtils.setApiAndSecretKey(exSpec);
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
