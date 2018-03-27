package org.knowm.xchange.examples.btcturk;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcturk.BTCTurkExchange;

/** @author semihunaldi */
public class BTCTurkDemoUtils {
  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new BTCTurkExchange().getDefaultExchangeSpecification();
    exSpec.setUserName("<insert_user_name>");
    exSpec.setApiKey("<insert_api_key>");
    exSpec.setSecretKey("<insert_secret_key>");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
