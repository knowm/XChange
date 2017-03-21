package org.knowm.xchange.huobi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.huobi.Huobi;

import si.mazi.rescu.RestProxyFactory;

public class HuobiBaseTradeService extends HuobiBaseService {
  protected final Huobi huobi;
  protected final String accessKey;
  protected final HuobiDigest digest;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected HuobiBaseTradeService(Exchange exchange) {

    super(exchange);

    huobi = RestProxyFactory.createProxy(Huobi.class, exchange.getExchangeSpecification().getSslUri());
    accessKey = exchange.getExchangeSpecification().getApiKey();
    digest = new HuobiDigest(exchange.getExchangeSpecification().getSecretKey(), "secret_key");
  }

  protected long nextCreated() {
    return System.currentTimeMillis() / 1000;
  }
}
