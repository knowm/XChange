package org.knowm.xchange.cryptopia.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptopia.Cryptopia;
import org.knowm.xchange.cryptopia.CryptopiaDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class CryptopiaBaseService extends BaseExchangeService implements BaseService {

  final Cryptopia cryptopia;
  final CryptopiaDigest signatureCreator;

  public CryptopiaBaseService(Exchange exchange) {

    super(exchange);
    this.cryptopia =
        RestProxyFactory.createProxy(
            Cryptopia.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    this.signatureCreator =
        CryptopiaDigest.createInstance(
            exchange.getNonceFactory(),
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey());
  }
}
