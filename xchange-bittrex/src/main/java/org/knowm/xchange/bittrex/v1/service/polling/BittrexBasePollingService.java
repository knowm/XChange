package org.knowm.xchange.bittrex.v1.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.v1.BittrexAuthenticated;
import org.knowm.xchange.bittrex.v1.service.BittrexDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BittrexBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final String apiKey;
  protected final BittrexAuthenticated bittrexAuthenticated;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexBasePollingService(Exchange exchange) {

    super(exchange);

    this.bittrexAuthenticated = RestProxyFactory.createProxy(BittrexAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BittrexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

}
