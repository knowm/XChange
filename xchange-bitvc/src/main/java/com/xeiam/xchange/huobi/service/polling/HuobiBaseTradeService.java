package com.xeiam.xchange.huobi.service.polling;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitvc.BitVcExchange;
import com.xeiam.xchange.bitvc.Huobi;
import com.xeiam.xchange.bitvc.service.BitVcDigest;
import com.xeiam.xchange.bitvc.service.polling.BitVcBasePollingService;


public class HuobiBaseTradeService extends BitVcBasePollingService {
  protected final Huobi huobi;
  protected final String accessKey;
  protected final BitVcDigest digest;

  protected HuobiBaseTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    
    huobi = RestProxyFactory.createProxy(Huobi.class, (String) exchangeSpecification.getExchangeSpecificParametersItem(BitVcExchange.URI_HUOBI));
    accessKey = exchangeSpecification.getApiKey();
    digest = new BitVcDigest(exchangeSpecification.getSecretKey());
  }

  protected long nextCreated() {
    return System.currentTimeMillis() / 1000;
  }
}
