package com.xeiam.xchange.bitvc.service.polling;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitvc.BitVc;
import com.xeiam.xchange.bitvc.service.BitVcDigest;

public class BitVcBaseTradeService extends BitVcBasePollingService {

  protected final BitVc bitvc;
  protected final String accessKey;
  protected final BitVcDigest digest;

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
    digest = new BitVcDigest(exchange.getExchangeSpecification().getSecretKey());
  }

  protected long nextCreated() {

    return System.currentTimeMillis() / 1000;
  }

}
