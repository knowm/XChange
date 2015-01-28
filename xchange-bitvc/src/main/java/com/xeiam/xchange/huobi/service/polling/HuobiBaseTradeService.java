package com.xeiam.xchange.huobi.service.polling;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitvc.BitVcExchange;
import com.xeiam.xchange.bitvc.Huobi;
import com.xeiam.xchange.bitvc.service.BitVcDigest;
import com.xeiam.xchange.bitvc.service.polling.BitVcBasePollingService;

public class HuobiBaseTradeService extends BitVcBasePollingService {
  protected final Huobi huobi;
  protected final String accessKey;
  protected final BitVcDigest digest;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected HuobiBaseTradeService(Exchange exchange) {

    super(exchange);

    huobi = RestProxyFactory.createProxy(Huobi.class,
        (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(BitVcExchange.URI_HUOBI));
    accessKey = exchange.getExchangeSpecification().getApiKey();
    digest = new BitVcDigest(exchange.getExchangeSpecification().getSecretKey());
  }

  protected long nextCreated() {
    return System.currentTimeMillis() / 1000;
  }
}
