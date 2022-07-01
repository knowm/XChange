package org.knowm.xchange.coinone.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinone.CoinoneAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class CoinoneBaseService extends BaseExchangeService implements BaseService {

  protected final CoinoneAuthenticated coinone;
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
  public CoinoneBaseService(Exchange exchange) {
    super(exchange);
    this.coinone =
        ExchangeRestProxyBuilder.forInterface(
                CoinoneAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.apiSecret = exchange.getExchangeSpecification().getSecretKey();
    this.url = exchange.getExchangeSpecification().getSslUri();
    if (apiSecret != null) {
      this.signatureCreator = CoinoneHmacDigest.createInstance(apiSecret);
      this.payloadCreator = CoinonePayloadDigest.createInstance();
    }
  }
}
