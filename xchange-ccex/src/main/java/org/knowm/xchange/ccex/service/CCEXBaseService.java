package org.knowm.xchange.ccex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ccex.CCEXAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author Andraž Prinčič
 */
public class CCEXBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final CCEXAuthenticated cCEXAuthenticated;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CCEXBaseService(Exchange exchange) {

    super(exchange);

    // allow HTTP connect- and read-timeout to be set per exchange
    ClientConfig rescuConfig = new ClientConfig(); // default rescu config
    int customHttpConnTimeout = exchange.getExchangeSpecification().getHttpConnTimeout();
    if (customHttpConnTimeout > 0) {
      rescuConfig.setHttpConnTimeout(customHttpConnTimeout);
    }
    int customHttpReadTimeout = exchange.getExchangeSpecification().getHttpReadTimeout();
    if (customHttpReadTimeout > 0) {
      rescuConfig.setHttpReadTimeout(customHttpReadTimeout);
    }

    this.cCEXAuthenticated = RestProxyFactory.createProxy(CCEXAuthenticated.class, exchange.getExchangeSpecification().getSslUri(), rescuConfig);
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = CCEXDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
