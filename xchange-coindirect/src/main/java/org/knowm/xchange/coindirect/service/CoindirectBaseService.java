package org.knowm.xchange.coindirect.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coindirect.CoindirectAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.ParamsDigest;

public class CoindirectBaseService extends BaseExchangeService implements BaseService {
  protected final Logger LOG = LoggerFactory.getLogger(getClass());

  protected final String apiKey;
  protected final CoindirectAuthenticated coindirect;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected CoindirectBaseService(Exchange exchange) {
    super(exchange);

    this.coindirect =
        ExchangeRestProxyBuilder.forInterface(
                CoindirectAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        CoindirectHawkDigest.createInstance(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getSecretKey());
  }
}
