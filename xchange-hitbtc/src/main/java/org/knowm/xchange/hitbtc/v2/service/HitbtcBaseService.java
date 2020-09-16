package org.knowm.xchange.hitbtc.v2.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ClientConfigCustomizer;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.hitbtc.v2.HitbtcAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ClientConfigUtil;

public class HitbtcBaseService extends BaseExchangeService implements BaseService {

  protected final HitbtcAuthenticated hitbtc;

  protected HitbtcBaseService(Exchange exchange) {

    super(exchange);

    String apiKey = exchange.getExchangeSpecification().getApiKey();
    String secretKey = exchange.getExchangeSpecification().getSecretKey();

    ClientConfigCustomizer clientConfigCustomizer =
        config -> ClientConfigUtil.addBasicAuthCredentials(config, apiKey, secretKey);
    hitbtc =
        ExchangeRestProxyBuilder.forInterface(
                HitbtcAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(clientConfigCustomizer)
            .build();
  }
}
