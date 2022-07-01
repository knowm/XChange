package org.knowm.xchange.btcturk.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcturk.BTCTurkAuthenticated;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

/**
 * @author semihunaldi
 * @author mertguner
 */
public class BTCTurkBaseService extends BaseExchangeService implements BaseService {

  protected final BTCTurkAuthenticated btcTurk;
  protected final ParamsDigest signatureCreator;

  public BTCTurkBaseService(Exchange exchange) {

    super(exchange);

    btcTurk =
        ExchangeRestProxyBuilder.forInterface(
                BTCTurkAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    signatureCreator =
        BTCTurkDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey());
  }
}
