package org.knowm.xchange.examples.cryptocom;

import static org.knowm.xchange.Exchange.USE_SANDBOX;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptocom.CryptoComExchange;
import org.knowm.xchange.utils.AuthUtils;

public class CryptoComDemoUtils {

  public static Exchange createExchange() {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CryptoComExchange.class);
    ExchangeSpecification cryptoComSpec = exchange.getDefaultExchangeSpecification();
    // The most convenient way. Can store all keys in .ssh folder
    AuthUtils.setApiAndSecretKey(cryptoComSpec, "cryptocom");
    cryptoComSpec.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    exchange.applySpecification(cryptoComSpec);

    return exchange;
  }
}
