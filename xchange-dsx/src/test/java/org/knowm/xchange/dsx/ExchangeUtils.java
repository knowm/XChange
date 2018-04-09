package org.knowm.xchange.dsx;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author Mikhail Wall */
public class ExchangeUtils {

  private static final Logger logger = LoggerFactory.getLogger(ExchangeUtils.class);

  public static Exchange createExchangeFromJsonConfiguration() throws IOException {

    ExchangeSpecification exSpec = new ExchangeSpecification(DSXExchange.class);
    ObjectMapper mapper = new ObjectMapper();
    InputStream is =
        ExchangeUtils.class.getClassLoader().getResourceAsStream("exchangeConfiguration.json");
    if (is == null) {
      logger.warn("No exchangeConfiguration.json file found. Returning null exchange.");
      return null;
    }
    try {
      ExchangeConfiguration conf = mapper.readValue(is, ExchangeConfiguration.class);
      logger.debug(conf.toString());

      if (conf.apiKey != null) {
        exSpec.setApiKey(conf.apiKey);
      }
      if (conf.secretKey != null) {
        exSpec.setSecretKey(conf.secretKey);
      }
      if (conf.sslUri != null) {
        exSpec.setSslUri(conf.sslUri);
      }
    } catch (Exception e) {
      logger.warn(
          "An exception occured while loading the exchangeConfiguration.json file from the classpath. "
              + "Returning null exchange. ",
          e);
      return null;
    }

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    exchange.remoteInit();
    return exchange;
  }
}
