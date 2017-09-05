package org.knowm.xchange.livecoin.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class LivecoinBaseService<T extends Livecoin> extends BaseExchangeService implements BaseService {

  protected final T service;
  protected final LivecoinDigest signatureCreator;
  protected final String apiKey;

  public LivecoinBaseService(Class<T> type, Exchange exchange) {
    super(exchange);

    this.service = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = LivecoinDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), apiKey);
  }
}
