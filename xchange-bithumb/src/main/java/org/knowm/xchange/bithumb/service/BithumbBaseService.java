package org.knowm.xchange.bithumb.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.Bithumb;
import org.knowm.xchange.bithumb.BithumbAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BithumbBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final Bithumb bithumb;
  protected final BithumbAuthenticated bithumbAuthenticated;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest endpointGenerator;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BithumbBaseService(Exchange exchange) {
    super(exchange);

    this.bithumbAuthenticated =
        RestProxyFactory.createProxy(
            BithumbAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BithumbDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.bithumb =
        RestProxyFactory.createProxy(
            Bithumb.class, exchange.getExchangeSpecification().getSslUri());
    this.endpointGenerator = new BithumbEndpointGenerator();
  }
}
