package com.xeiam.xchange.bitvc.service.polling;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitvc.BitVc;
import com.xeiam.xchange.bitvc.service.BitVcDigest;

public class BitVcBaseTradeService extends BitVcBasePollingService {

  protected final BitVc bitvc;
  protected final String accessKey;
  protected final BitVcDigest digest;

  protected BitVcBaseTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    final String baseUrl = exchangeSpecification.getSslUri();
    bitvc = RestProxyFactory.createProxy(BitVc.class, baseUrl);
    accessKey = exchangeSpecification.getApiKey();
    digest = new BitVcDigest(exchangeSpecification.getSecretKey());
  }

  protected long nextCreated() {

    return System.currentTimeMillis() / 1000;
  }

}
