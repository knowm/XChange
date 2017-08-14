package org.knowm.xchange.examples.cryptopia;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptopia.CryptopiaExchange;

public class CryptopiaDemoUtils {

  public static Exchange createExchange() {
    ExchangeSpecification exchangeSpecification = new CryptopiaExchange().getDefaultExchangeSpecification();
    exchangeSpecification.setApiKey(null);
    exchangeSpecification.setSecretKey(null);

    return ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
  }
}
