package org.knowm.xchange.examples.cryptonit2;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptonit2.CryptonitExchange;

/** @author Matija Mazi */
public class CryptonitDemoUtils {

  public static Exchange createExchange() {
/**/
    ExchangeSpecification exSpec = new CryptonitExchange().getDefaultExchangeSpecification();
    exSpec.setUserName("209");
    exSpec.setApiKey("04B5F8C2-E8A0-4AE2-ACC7-CBA0A564F8C0");
    exSpec.setSecretKey("Mzk5OTM2OTNhNmUyOGY1NmY4MTM2NDgyMTM2NTA0YmYwMzg2MTQxYg");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
