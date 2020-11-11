package org.knowm.xchange.coinfloor.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ClientConfigCustomizer;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinfloor.CoinfloorAuthenticated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.ClientConfigUtil;

public class CoinfloorAuthenticatedService extends CoinfloorService {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  protected CoinfloorAuthenticated coinfloor;

  protected CoinfloorAuthenticatedService(Exchange exchange) {
    super(exchange);

    ExchangeSpecification specification = exchange.getExchangeSpecification();

    if (specification.getUserName() == null || specification.getPassword() == null) {
      logger.info(
          "Authenticated endpoints are not available - username and password have not been configured");
      coinfloor = null;
      return;
    }

    ClientConfigCustomizer clientConfigCustomizer =
        config ->
            ClientConfigUtil.addBasicAuthCredentials(
                config, specification.getUserName(), specification.getPassword());
    coinfloor =
        ExchangeRestProxyBuilder.forInterface(
                CoinfloorAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(clientConfigCustomizer)
            .build();
  }
}
