package org.knowm.xchange.gateio.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gateio.GateioAuthenticated;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class GateioBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final GateioAuthenticated bter;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioBaseService(Exchange exchange) {

    super(exchange);

    this.bter =
        ExchangeRestProxyBuilder.forInterface(
                GateioAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        GateioHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected <R extends GateioBaseResponse> R handleResponse(R response) {

    if (!response.isResult()) {
      throw new ExchangeException(response.getMessage());
    }

    return response;
  }

  public String getApiKey() {
    return apiKey;
  }
}
