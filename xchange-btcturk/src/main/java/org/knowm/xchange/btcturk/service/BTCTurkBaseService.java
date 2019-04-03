package org.knowm.xchange.btcturk.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcturk.BTCTurkAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

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
        RestProxyFactory.createProxy(
            BTCTurkAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    signatureCreator =
        BTCTurkDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey());
  }
}
