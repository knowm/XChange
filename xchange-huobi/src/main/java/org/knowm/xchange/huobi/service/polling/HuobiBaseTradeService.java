package org.knowm.xchange.huobi.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.huobi.Huobi;
import org.knowm.xchange.huobi.service.HuobiDigest;

import si.mazi.rescu.RestProxyFactory;

public class HuobiBaseTradeService extends HuobiBasePollingService {
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
    digest = new HuobiDigest(exchange.getExchangeSpecification().getSecretKey());
  }

  protected long nextCreated() {
    return System.currentTimeMillis() / 1000;
  }
}
