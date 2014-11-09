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
    exSpec.setSecretKey("c46f7d46-d16a-470a-8bfc-0898493dc118");
    exSpec.setApiKey("1c5f7bf9-1857-4bb6-a24d-75ea92bfff86");
    
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

}
