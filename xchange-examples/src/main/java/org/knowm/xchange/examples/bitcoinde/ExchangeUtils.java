package org.knowm.xchange.examples.bitcoinde;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExchangeUtils {

  private static final Logger logger = LoggerFactory.getLogger(ExchangeUtils.class);

  /**
   * Create a exchange using the keys provided in a bitcoinde/exchangeConfiguration.json file on the
   * classpath.
   *
   * @return Create exchange or null if .json file was not on classpath.
   */
  public static Exchange createExchangeFromJsonConfiguration() throws IOException {

    ExchangeSpecification exSpec = new ExchangeSpecification(BitcoindeExchange.class);
    ObjectMapper mapper = new ObjectMapper();
    InputStream is =
        ExchangeUtils.class
            .getClassLoader()
            .getResourceAsStream("bitcoinde/exchangeConfiguration.json");
    if (is == null) {
      logger.warn("No bitcoinde/exchangeConfiguration.json file found. Returning null exchange.");
      return null;
    }
    try {
      ExchangeConfiguration conf = mapper.readValue(is, ExchangeConfiguration.class);
      logger.debug(conf.toString());

      if (conf.apiKey != null) exSpec.setApiKey(conf.apiKey);
      if (conf.secretKey != null) exSpec.setSecretKey(conf.secretKey);
    } catch (Exception e) {
      logger.warn(
          "An exception occured while loading the bitcoinde/exchangeConfiguration.json file from the classpath. "
              + "Returning null exchange.",
          e);
      return null;
    }

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    exchange.remoteInit();
    return exchange;
  }
}
