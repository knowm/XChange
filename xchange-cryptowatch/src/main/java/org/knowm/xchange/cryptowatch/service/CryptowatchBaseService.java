package org.knowm.xchange.cryptowatch.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptowatch.Cryptowatch;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class CryptowatchBaseService extends BaseExchangeService implements BaseService {

  protected Cryptowatch cryptowatch;

  public CryptowatchBaseService(Exchange exchange) {
    super(exchange);
    cryptowatch =
        RestProxyFactory.createProxy(
            Cryptowatch.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }
}
