package org.knowm.xchange.upbit.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.upbit.UpbitAuthenticated;
import si.mazi.rescu.ParamsDigest;

public class UpbitBaseService extends BaseExchangeService implements BaseService {

  protected final UpbitAuthenticated upbit;
  protected final String apiKey;
  protected final String apiSecret;
  protected final String url;
  protected ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public UpbitBaseService(Exchange exchange) {
    super(exchange);
    this.upbit =
        ExchangeRestProxyBuilder.forInterface(
                UpbitAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.apiSecret = exchange.getExchangeSpecification().getSecretKey();
    this.url = exchange.getExchangeSpecification().getSslUri();
    this.signatureCreator = UpbitJWTDigest.createInstance(this.apiKey, this.apiSecret);
  }
}
