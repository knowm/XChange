package org.knowm.xchange.hitbtc.v2.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.hitbtc.v2.HitbtcAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.ClientConfigUtil;
import si.mazi.rescu.RestProxyFactory;

public class HitbtcBaseService extends BaseExchangeService implements BaseService {

  protected final HitbtcAuthenticated hitbtc;

  protected HitbtcBaseService(Exchange exchange) {

    super(exchange);

    String apiKey = exchange.getExchangeSpecification().getApiKey();
    String secretKey = exchange.getExchangeSpecification().getSecretKey();

    ClientConfig config = getClientConfig();
    ClientConfigUtil.addBasicAuthCredentials(config, apiKey, secretKey);
    hitbtc =
        RestProxyFactory.createProxy(
            HitbtcAuthenticated.class, exchange.getExchangeSpecification().getSslUri(), config);
  }
}
