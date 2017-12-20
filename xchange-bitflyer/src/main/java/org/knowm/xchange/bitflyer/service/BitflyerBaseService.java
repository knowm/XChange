package org.knowm.xchange.bitflyer.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.Bitflyer;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BitflyerBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final Bitflyer bitflyer;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitflyerBaseService(Exchange exchange) {

    super(exchange);

    this.bitflyer = RestProxyFactory.createProxy(Bitflyer.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BitflyerDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getApiKey());
  }

}
