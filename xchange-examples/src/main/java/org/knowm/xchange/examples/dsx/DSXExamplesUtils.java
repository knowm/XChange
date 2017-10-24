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
    exSpec.setSecretKey("6FY66EC0YJ7YL4OHAC1X5ZEYAUUFRJBFI21I9S7A5PW7");
    exSpec.setApiKey("396f12b3-9998-4ff8-800b-f29ab6d8e886");
    exSpec.setSslUri("https://dsx.uk");
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    exchange.remoteInit();
    return exchange;
  }
}
