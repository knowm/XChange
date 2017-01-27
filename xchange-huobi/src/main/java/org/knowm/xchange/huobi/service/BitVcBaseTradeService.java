package org.knowm.xchange.huobi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.huobi.BitVc;

import si.mazi.rescu.RestProxyFactory;

public class BitVcBaseTradeService extends HuobiBaseService {

  protected final BitVc bitvc;
  protected final String accessKey;
  protected HuobiDigest digest;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitVcBaseTradeService(Exchange exchange) {

    super(exchange);

    final String baseUrl = exchange.getExchangeSpecification().getSslUri();
    bitvc = RestProxyFactory.createProxy(BitVc.class, baseUrl);
    accessKey = exchange.getExchangeSpecification().getApiKey();
    digest = new HuobiDigest(exchange.getExchangeSpecification().getSecretKey(), "secret_key");
  }

  protected long nextCreated() {

    return System.currentTimeMillis() / 1000;
  }

}
