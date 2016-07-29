package org.knowm.xchange.bter.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bter.BTERAuthenticated;
import org.knowm.xchange.bter.dto.BTERBaseResponse;
import org.knowm.xchange.bter.service.BTERHmacPostBodyDigest;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BTERBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final String apiKey;
  protected final BTERAuthenticated bter;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTERBasePollingService(Exchange exchange) {

    super(exchange);

    this.bter = RestProxyFactory.createProxy(BTERAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BTERHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected <R extends BTERBaseResponse> R handleResponse(R response) {

    if (!response.isResult()) {
      throw new ExchangeException(response.getMessage());
    }

    return response;
  }

}
