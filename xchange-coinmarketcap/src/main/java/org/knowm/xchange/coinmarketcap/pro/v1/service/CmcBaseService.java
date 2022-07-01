package org.knowm.xchange.coinmarketcap.pro.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinmarketcap.pro.v1.CmcAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

class CmcBaseService extends BaseExchangeService implements BaseService {

  final String apiKey;
  final CmcAuthenticated cmcAuthenticated;

  CmcBaseService(Exchange exchange) {
    super(exchange);

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.cmcAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                CmcAuthenticated.class, exchange.getExchangeSpecification())
            .build();
  }
}
