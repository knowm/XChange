package org.knowm.xchange.examples.kucoin;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kucoin.KucoinExchange;

public class KucoinExamplesUtils {

  public static Exchange getExchange() {

    if (System.getProperty("kucoin-api-key") == null) {
      throw new IllegalArgumentException(
          "To run the examples, call using:\n"
              + "  -Dkucoin-api-key=YOURAPIKEY -Dkucoin-api-secret=YOURSECRET \n"
              + "  -Dkucoin-api-passphrase=YOURPASSPHRASE -Dkucoin-sandbox=true");
    }

    ExchangeSpecification exSpec = new ExchangeSpecification(KucoinExchange.class);
    exSpec.setApiKey(System.getProperty("kucoin-api-key"));
    exSpec.setSecretKey(System.getProperty("kucoin-api-secret"));
    exSpec.setExchangeSpecificParametersItem(
        "passphrase", System.getProperty("kucoin-api-passphrase"));
    exSpec.setExchangeSpecificParametersItem(
        KucoinExchange.PARAM_SANDBOX, Boolean.valueOf(System.getProperty("kucoin-sandbox")));

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
