package org.knowm.xchange.examples.cexio;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cexio.CexIOExchange;

/** Author: brox Since: 2/6/14 */
public class CexIODemoUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(CexIOExchange.class);
    exSpec.setUserName("");
    exSpec.setSecretKey("");
    exSpec.setApiKey("");
    exSpec.setSslUri("https://cex.io");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
