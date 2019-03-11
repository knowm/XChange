package org.knowm.xchange.examples.lakebtc;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.lakebtc.LakeBTCExchange;

/** Created by cristian.lucaci on 12/19/2014. */
public class LakeBTCExamplesUtils {

  private LakeBTCExamplesUtils() {}

  public static Exchange createTestExchange() {

    Exchange lakeBtcExchange =
        ExchangeFactory.INSTANCE.createExchange(LakeBTCExchange.class.getName());

    //    lakeBtcExchange.getExchangeSpecification().setSslUri("https://www.LakeBTC.com");
    //    lakeBtcExchange.getExchangeSpecification().setHost("https://lakebtc.com");
    //    lakeBtcExchange.getExchangeSpecification().setPort(80);
    //    lakeBtcExchange.getExchangeSpecification().setApiKey("API Key");
    //    lakeBtcExchange.getExchangeSpecification().setSecretKey("Secret==");
    //    lakeBtcExchange.getExchangeSpecification().setUserName("email");
    //    lakeBtcExchange.applySpecification(lakeBtcExchange.getExchangeSpecification());
    return lakeBtcExchange;
  }
}
