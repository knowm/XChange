package org.knowm.xchange.coindirect;

import java.io.InputStream;
import java.util.Properties;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExchangeUtils {
  private static final Logger logger = LoggerFactory.getLogger(ExchangeUtils.class);

  public static Exchange createExchangeFromProperties() {

    ExchangeSpecification exSpec = new ExchangeSpecification(CoindirectExchange.class);
    InputStream is =
        ExchangeUtils.class.getResourceAsStream(
            "/org/knowm/xchange/coindirect/dto/configuration.properties");
    Properties props = new Properties();

    try {
      props.load(is);
      logger.debug(props.toString());

      if (props.getProperty("api-key") != null) exSpec.setApiKey(props.getProperty("api-key"));
      if (props.getProperty("secret-key") != null)
        exSpec.setSecretKey(props.getProperty("secret-key"));
    } catch (Exception e) {
      logger.warn(
          "An exception occured while loading the configuration file from the classpath. "
              + "Returning exchange without keys.",
          e);
      return ExchangeFactory.INSTANCE.createExchange(CoindirectExchange.class.getName());
    }

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
