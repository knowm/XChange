package org.knowm.xchange.examples.upbit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.upbit.UpbitExchange;

/**
 * @author interwater
 * todo 
 * for account, trade.. etc
 */
public class UpbitDemoUtils {
  public static Exchange createExchange() {
    ExchangeSpecification exSpec = new UpbitExchange().getDefaultExchangeSpecification();
    exSpec.setApiKey("PublicKeyGeneratedUopnLogin");
    exSpec.setSecretKey("SecretKeyGeneratedUopnLogin");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
