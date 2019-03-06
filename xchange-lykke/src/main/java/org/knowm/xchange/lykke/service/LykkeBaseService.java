package org.knowm.xchange.lykke.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.lykke.Lykke;
import org.knowm.xchange.lykke.LykkeAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class LykkeBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final LykkeAuthenticated lykke;
  protected final Lykke lykkePublic;

  protected LykkeBaseService(Exchange exchange) {
    super(exchange);
    this.lykke =
        RestProxyFactory.createProxy(
            LykkeAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.lykkePublic =
        RestProxyFactory.createProxy(
            LykkeAuthenticated.class, "https://public-api.lykke.com/", getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
  }
}
