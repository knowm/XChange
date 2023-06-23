package org.knowm.xchange.gateio.service;

import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gateio.Gateio;
import org.knowm.xchange.gateio.GateioAuthenticated;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class GateioBaseService extends BaseExchangeService<GateioExchange> implements BaseService {

  protected final String apiKey;
  protected final Gateio gateio;
  protected final GateioAuthenticated gateioAuthenticated;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioBaseService(GateioExchange exchange) {

    super(exchange);

    gateio = ExchangeRestProxyBuilder
        .forInterface(Gateio.class, exchange.getExchangeSpecification())
        .build();
    gateioAuthenticated = ExchangeRestProxyBuilder
        .forInterface(GateioAuthenticated.class, exchange.getExchangeSpecification())
        .build();
    apiKey = exchange.getExchangeSpecification().getApiKey();
    signatureCreator =
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
