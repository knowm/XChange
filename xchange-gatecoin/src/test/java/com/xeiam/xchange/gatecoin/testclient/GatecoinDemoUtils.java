
package com.xeiam.xchange.gatecoin.testclient;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.gatecoin.GatecoinExchange;

/**
 *
 * @author sumdeha
 */
public class GatecoinDemoUtils {
    public static Exchange createExchange() {

    ExchangeSpecification exSpec = new GatecoinExchange().getDefaultExchangeSpecification();
    exSpec.setUserName("username");
    exSpec.setApiKey("PublicKeyGeneratedUopnLogin");
    exSpec.setSecretKey("SecretKeyGeneratedUopnLogin");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
