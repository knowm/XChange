package org.knowm.xchange.kucoin.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.kucoin.Kucoin;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class KucoinBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final Kucoin kucoin;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected KucoinBaseService(Exchange exchange) {
    super(exchange);
    this.kucoin = RestProxyFactory.createProxy(Kucoin.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = null; //BinanceHmacDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

}
