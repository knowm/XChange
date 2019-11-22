package org.knowm.xchange.bitfinex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v1.BitfinexAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BitfinexBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final BitfinexAuthenticated bitfinex;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest signatureCreatorV2;
  protected final ParamsDigest payloadCreator;

  protected final org.knowm.xchange.bitfinex.v2.BitfinexAuthenticated bitfinexV2;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexBaseService(Exchange exchange) {

    super(exchange);

    this.bitfinex =
        RestProxyFactory.createProxy(
            BitfinexAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BitfinexHmacPostBodyDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey());
    this.signatureCreatorV2 =
        BitfinexHmacPostBodyDigestV2.createInstance(
            exchange.getExchangeSpecification().getSecretKey());
    this.payloadCreator = new BitfinexPayloadDigest();

    this.bitfinexV2 =
        RestProxyFactory.createProxy(
            org.knowm.xchange.bitfinex.v2.BitfinexAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
  }
}
