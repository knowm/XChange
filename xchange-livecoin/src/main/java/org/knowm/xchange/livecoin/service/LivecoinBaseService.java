package org.knowm.xchange.livecoin.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class LivecoinBaseService<T extends Livecoin> extends BaseExchangeService
    implements BaseService {

  protected final Livecoin service;
  protected final LivecoinDigest signatureCreator;
  protected final String apiKey;

  public LivecoinBaseService(Exchange exchange, Livecoin livecoin) {
    super(exchange);

    this.service = livecoin;
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        LivecoinDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), apiKey);
  }
}
