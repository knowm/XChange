package org.knowm.xchange.hitbtc.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.hitbtc.HitbtcAuthenticated;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.ClientConfigUtil;
import si.mazi.rescu.RestProxyFactory;

public class HitbtcAuthenitcatedService extends HitbtcBaseService {

  private final HitbtcAuthenticated hitbtc;
  protected final String apiKey;
  protected final String secretKey;

  public HitbtcAuthenitcatedService(Exchange exchange) {
    super(exchange);

    apiKey = exchange.getExchangeSpecification().getApiKey();
    secretKey = exchange.getExchangeSpecification().getSecretKey();

    ClientConfig config = new ClientConfig();
    ClientConfigUtil.addBasicAuthCredentials(config, apiKey, secretKey);

    hitbtc = RestProxyFactory.createProxy(HitbtcAuthenticated.class, exchange.getExchangeSpecification().getSslUri(), config);
  }

  public HitbtcAuthenticated hitbtc() {
    Validate.isTrue(StringUtils.isNotEmpty(apiKey), "Authenticated endpoints are not available: missing ApiKey");
    Validate.isTrue(StringUtils.isNotEmpty(secretKey), "Authenticated endpoints are not available: missing SecretKey");

    return hitbtc;
  }

}
