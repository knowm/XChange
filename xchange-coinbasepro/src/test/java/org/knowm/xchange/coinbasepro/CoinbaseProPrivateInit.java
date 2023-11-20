package org.knowm.xchange.coinbasepro;

import java.io.IOException;
import java.util.Properties;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

public class CoinbaseProPrivateInit {

  public static Exchange getCoinbasePrivateInstance() {
    Properties properties = new Properties();

    try {
      properties.load(CoinbaseProPrivateInit.class.getResourceAsStream("/secret.keys"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ExchangeSpecification spec = new CoinbaseProExchange().getDefaultExchangeSpecification();

    spec.setApiKey(properties.getProperty("coinbaseApi"));
    spec.setSecretKey(properties.getProperty("coinbaseSecret"));
    spec.setExchangeSpecificParametersItem("passphrase", properties.getProperty("coinbasePassphrase"));
    spec.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, true);

    return ExchangeFactory.INSTANCE.createExchange(spec);
  }
}
