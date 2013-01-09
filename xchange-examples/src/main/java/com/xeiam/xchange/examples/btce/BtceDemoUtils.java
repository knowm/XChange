package com.xeiam.xchange.examples.btce;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.BTCEExchange;

/**
 * @author Matija Mazi <br/>
 */
public class BtceDemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(BTCEExchange.class);
    exSpec.setSecretKey("be7ea6f91c627097dac1526c4691e4e98c732b7af3cfe85c8472075475fe7536");
    exSpec.setApiKey("SIUNFHSF-9HKXHVKG-FTHTGGH1-62CYKF11-R0YANNCV");
    exSpec.setUri("https://btc-e.com");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
