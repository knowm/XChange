package org.knowm.xchange.huobi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.huobi.BitVc;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.RestProxyFactory;

public class BitVcBaseServiceRaw extends BaseExchangeService implements BaseService  {

  protected final BitVc bitvc;
  protected final String accessKey;
  protected HuobiDigest digest;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitVcBaseServiceRaw(Exchange exchange) {

    super(exchange);

    final String baseUrl = exchange.getExchangeSpecification().getSslUri();
    bitvc = RestProxyFactory.createProxy(BitVc.class, baseUrl, getClientConfig());
    accessKey = exchange.getExchangeSpecification().getApiKey();
    digest = new HuobiDigest(exchange.getExchangeSpecification().getSecretKey(), "secret_key");
  }

  protected long nextCreated() {

    return System.currentTimeMillis() / 1000;
  }

}
