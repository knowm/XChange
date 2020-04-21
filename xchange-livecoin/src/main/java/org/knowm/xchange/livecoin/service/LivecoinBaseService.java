package org.knowm.xchange.livecoin.service;

import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinDigest;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.service.BaseResilientExchangeService;
import org.knowm.xchange.service.BaseService;

public class LivecoinBaseService extends BaseResilientExchangeService<LivecoinExchange>
    implements BaseService {

  protected final Livecoin service;
  protected final LivecoinDigest signatureCreator;
  protected final String apiKey;

  public LivecoinBaseService(
      LivecoinExchange exchange, Livecoin livecoin, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);

    this.service = livecoin;
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        LivecoinDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), apiKey);
  }
}
