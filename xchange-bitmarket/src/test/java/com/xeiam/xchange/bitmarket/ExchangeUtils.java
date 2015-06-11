package com.xeiam.xchange.bitmarket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author by kfonal
 */
public class ExchangeUtils {
  private final static Logger logger = LoggerFactory.getLogger(ExchangeUtils.class);

  public static Exchange createExchangeFromProperties() {

    ExchangeSpecification exSpec = new ExchangeSpecification(BitMarketExchange.class);
    InputStream is = ExchangeUtils.class.getResourceAsStream("/configuration.properties");
    Properties props = new Properties();

    try {
      props.load(is);
      logger.debug(props.toString());

      if (props.getProperty("api-key") != null) exSpec.setApiKey(props.getProperty("api-key"));
      if (props.getProperty("secret-key") != null) exSpec.setSecretKey(props.getProperty("secret-key"));
    } catch (Exception e) {
      logger.warn("An exception occured while loading the v3/exchangeConfiguration.json file from the classpath. " +
          "Returning null exchange.", e);
      return null;
    }

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
