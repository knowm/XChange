package org.knowm.xchange.bittrex.service;

import org.knowm.xchange.bittrex.BittrexAuthenticated;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.service.BaseResilientExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class BittrexBaseService extends BaseResilientExchangeService<BittrexExchange>
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
  public BittrexBaseService(
      BittrexExchange exchange,
      BittrexAuthenticated bittrex,
      ResilienceRegistries resilienceRegistries) {

    super(exchange, resilienceRegistries);
    this.bittrexAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BittrexAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.contentCreator =
        BittrexContentDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.signatureCreator =
        BittrexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
