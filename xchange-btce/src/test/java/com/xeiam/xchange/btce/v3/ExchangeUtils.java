package com.xeiam.xchange.btce.v3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * @author Peter N. Steinmetz
 * Date: 3/30/15
 * Time: 4:28 PM
 */
public class ExchangeUtils {
  private final static Logger logger = LoggerFactory.getLogger(ExchangeUtils.class);

  /**
   * Create a BTC-e exchange using the keys provided in a v3/exchangeConfiguration.json
   * file on the classpath.
   *
   * See the v3/sampleExchangeConfiguration.json file for format of required file.
   *
   * @return Create exchange or null if .json file was not on classpath.
   */
  public static Exchange createExchangeFromJsonConfiguration() {

    ExchangeSpecification exSpec = new ExchangeSpecification(BTCEExchange.class);
    ObjectMapper mapper = new ObjectMapper();
    InputStream is = ExchangeUtils.class.getClassLoader().getResourceAsStream("v3/exchangeConfiguration.json");
    try {
      ExchangeConfiguration conf = mapper.readValue(is, ExchangeConfiguration.class);
      logger.debug(conf.toString());

      if (conf.apiKey != null) exSpec.setApiKey(conf.apiKey);
      if (conf.secretKey != null) exSpec.setSecretKey(conf.secretKey);
      if (conf.sslUri != null) exSpec.setSslUri(conf.sslUri);
    } catch (Exception e) {
      logger.error("An exception occured while loading the exchangeConfiguration.json file from the classpath. " +
         "Return null exchange.", e);
      return null;
    }

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

}
