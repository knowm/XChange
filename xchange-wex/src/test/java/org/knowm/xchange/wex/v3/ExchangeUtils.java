package org.knowm.xchange.wex.v3;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author Peter N. Steinmetz Date: 3/30/15 Time: 4:28 PM */
public class ExchangeUtils {
  private static final Logger logger = LoggerFactory.getLogger(ExchangeUtils.class);

  /**
   * Create a BTC-e exchange using the keys provided in a v3/exchangeConfiguration.json file on the
   * classpath. See the v3/sampleExchangeConfiguration.json file for format of required file.
   *
   * @return Create exchange or null if .json file was not on classpath.
   */
  public static Exchange createExchangeFromJsonConfiguration() throws IOException {

    ExchangeSpecification exSpec = new ExchangeSpecification(WexExchange.class);
    ObjectMapper mapper = new ObjectMapper();
    InputStream is =
        ExchangeUtils.class
            .getClassLoader()
            .getResourceAsStream("org/knowm/xchange/wex/v3/exchangeConfiguration.json");
    if (is == null) {
      logger.warn("No v3/exchangeConfiguration.json file found. Returning null exchange.");
      return null;
    }
    try {
      ExchangeConfiguration conf = mapper.readValue(is, ExchangeConfiguration.class);
      logger.debug(conf.toString());

      if (conf.apiKey != null) exSpec.setApiKey(conf.apiKey);
      if (conf.secretKey != null) exSpec.setSecretKey(conf.secretKey);
      if (conf.sslUri != null) exSpec.setSslUri(conf.sslUri);
    } catch (Exception e) {
      logger.warn(
          "An exception occured while loading the v3/exchangeConfiguration.json file from the classpath. "
              + "Returning null exchange.",
          e);
      return null;
    }

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    exchange.remoteInit();
    return exchange;
  }
}
