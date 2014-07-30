package com.xeiam.xchange.examples.poloniex;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.poloniex.PoloniexExchange;

/**
 * @author Zach Holmes
 */

public class PoloniexExamplesUtils {

  public static Exchange getExchange() {

    ExchangeSpecification spec = new ExchangeSpecification(PoloniexExchange.class);
    spec.setApiKey("FJ20SJ3J-CA0Y40HF-BYDGCJ8P-IA97N61P");
    spec.setSecretKey("fj30fj20bae9925f68ab81e90abc7b1a99783bbbf1f34a7f52c973998161ad570a067303d6ce0e5f6430c210908316ee13998044be8760c62f2de090ab2c93a9");
    
    return ExchangeFactory.INSTANCE.createExchange(spec);
  }
}
