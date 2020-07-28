package org.knowm.xchange.bittrex.service;

import org.knowm.xchange.bittrex.BittrexAuthenticated;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BittrexBaseService extends BaseExchangeService<BittrexExchange>
    implements BaseService {

  protected final String apiKey;
  protected final BittrexAuthenticated bittrexAuthenticated;
  protected final ParamsDigest contentCreator;
  protected final BittrexDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexBaseService(BittrexExchange exchange) {

    super(exchange);
    this.bittrexAuthenticated =
        RestProxyFactory.createProxy(
            BittrexAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            ExchangeRestProxyBuilder.createClientConfig(exchange.getExchangeSpecification()));
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.contentCreator =
        BittrexContentDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.signatureCreator =
        BittrexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
