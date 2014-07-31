package com.xeiam.xchange.examples.btcchina;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChinaExchange;

/**
 * @author david-yam
 */
public class BTCChinaExamplesUtils {

  public static Exchange getExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(BTCChinaExchange.class);
    exSpec.setSecretKey("dea20500-3a2f-4038-ace1-8e9bd244e56c");
    exSpec.setApiKey("9dfa65ee-e023-48fb-a8c1-cba6ea4b01ff");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

}
