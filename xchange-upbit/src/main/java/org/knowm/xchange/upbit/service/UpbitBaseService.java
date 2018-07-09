package org.knowm.xchange.upbit.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.upbit.UpbitAuthenticated;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class UpbitBaseService extends BaseExchangeService implements BaseService {

  protected final UpbitAuthenticated upbit;
  protected final String apiKey;
  protected final String apiSecret;
  protected final String url;
  protected ParamsDigest signatureCreator;
  protected ParamsDigest payloadCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public UpbitBaseService(Exchange exchange) {
    super(exchange);
    this.upbit =
        RestProxyFactory.createProxy(
            UpbitAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.apiSecret = exchange.getExchangeSpecification().getSecretKey();
    this.url = exchange.getExchangeSpecification().getSslUri();
  }
}
