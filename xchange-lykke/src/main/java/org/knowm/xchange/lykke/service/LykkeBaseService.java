package org.knowm.xchange.lykke.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.lykke.Lykke;
import org.knowm.xchange.lykke.LykkeAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class LykkeBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final LykkeAuthenticated lykke;
  protected final Lykke lykkePublic;

  protected LykkeBaseService(Exchange exchange) {
    super(exchange);
    this.lykke =
        ExchangeRestProxyBuilder.forInterface(
                LykkeAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.lykkePublic =
        ExchangeRestProxyBuilder.forInterface(
                LykkeAuthenticated.class, exchange.getExchangeSpecification())
            .baseUrl("https://public-api.lykke.com/")
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
  }
}
