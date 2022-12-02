package org.knowm.xchange.cryptopia.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.cryptopia.Cryptopia;
import org.knowm.xchange.cryptopia.CryptopiaDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class CryptopiaBaseService extends BaseExchangeService implements BaseService {

  final Cryptopia cryptopia;
  final CryptopiaDigest signatureCreator;

  public CryptopiaBaseService(Exchange exchange) {

    super(exchange);
    this.cryptopia =
        ExchangeRestProxyBuilder.forInterface(Cryptopia.class, exchange.getExchangeSpecification())
            .build();
    this.signatureCreator =
        CryptopiaDigest.createInstance(
            exchange.getNonceFactory(),
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey());
  }
}
