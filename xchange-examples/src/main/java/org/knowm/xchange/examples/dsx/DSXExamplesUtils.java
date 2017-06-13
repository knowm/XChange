package org.knowm.xchange.examples.dsx;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dsx.DSXExchange;

/**
 * @author Mikhail Wall
 */
public class DSXExamplesUtils {

  public static Exchange createExchange() throws IOException {

    ExchangeSpecification exSpec = new ExchangeSpecification(DSXExchange.class);
    exSpec.setSecretKey("sk_secret");
    exSpec.setApiKey("sk_key");
    exSpec.setSslUri("http://localhost");
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    exchange.remoteInit();
    return exchange;
  }
}
