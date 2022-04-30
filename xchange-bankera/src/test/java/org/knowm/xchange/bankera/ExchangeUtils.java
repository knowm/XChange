package org.knowm.xchange.bankera;

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

    ExchangeSpecification exSpec = new ExchangeSpecification(BankeraExchange.class);

    InputStream is =
        ExchangeUtils.class.getResourceAsStream(
            "/org/knowm/xchange/bankera/configuration.properties");
    Properties props = new Properties();
    exSpec.setProxyHost("localhost");
    exSpec.setProxyPort(1080);
    try {
      props.load(is);
      logger.debug(props.toString());

      if (props.getProperty("client-id") != null) {
        exSpec.setExchangeSpecificParametersItem("clientId", props.getProperty("client-id"));
      }
      if (props.getProperty("client-secret") != null) {
        exSpec.setExchangeSpecificParametersItem(
            "clientSecret", props.getProperty("client-secret"));
      }
    } catch (Exception e) {
      logger.warn("Error while loading integration test properties", e);
      return null;
    }

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
