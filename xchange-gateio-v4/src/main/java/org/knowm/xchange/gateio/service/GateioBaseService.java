package org.knowm.xchange.gateio.service;

import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gateio.Gateio;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.GateioV4Authenticated;
import org.knowm.xchange.gateio.config.GateioJacksonObjectMapperFactory;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class GateioBaseService extends BaseExchangeService<GateioExchange> implements BaseService {

  protected final String apiKey;
  protected final Gateio gateio;
  protected final GateioV4Authenticated gateioV4Authenticated;
  protected final ParamsDigest gateioV4ParamsDigest;


  public GateioBaseService(GateioExchange exchange) {
    super(exchange);

    gateio = ExchangeRestProxyBuilder
        .forInterface(Gateio.class, exchange.getExchangeSpecification())
        .clientConfigCustomizer(clientConfig -> clientConfig.setJacksonObjectMapperFactory(new GateioJacksonObjectMapperFactory()))
        .build();
    apiKey = exchange.getExchangeSpecification().getApiKey();

    gateioV4Authenticated = ExchangeRestProxyBuilder
        .forInterface(GateioV4Authenticated.class, exchange.getExchangeSpecification())
        .clientConfigCustomizer(clientConfig -> clientConfig.setJacksonObjectMapperFactory(new GateioJacksonObjectMapperFactory()))
        .build();

    gateioV4ParamsDigest =
        GateioV4Digest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected <R extends GateioBaseResponse> R handleResponse(R response) {

    if (!response.isResult()) {
      throw new ExchangeException(response.getMessage());
    }

    return response;
  }

}
