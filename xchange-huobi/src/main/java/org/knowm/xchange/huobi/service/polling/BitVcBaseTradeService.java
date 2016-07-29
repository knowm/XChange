package org.knowm.xchange.huobi.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.huobi.BitVc;
import org.knowm.xchange.huobi.service.HuobiDigest;

import si.mazi.rescu.RestProxyFactory;

public class BitVcBaseTradeService extends HuobiBasePollingService {

  protected final BitVc bitvc;
  protected final String accessKey;
  protected final HuobiDigest digest;

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
    digest = new HuobiDigest(exchange.getExchangeSpecification().getSecretKey());
  }

  protected long nextCreated() {

    return System.currentTimeMillis() / 1000;
  }

}
