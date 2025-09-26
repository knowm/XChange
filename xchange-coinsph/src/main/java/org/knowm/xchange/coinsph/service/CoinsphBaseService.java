package org.knowm.xchange.coinsph.service;

import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinsph.Coinsph;
import org.knowm.xchange.coinsph.CoinsphAuthenticated;
import org.knowm.xchange.coinsph.CoinsphExchange;
import org.knowm.xchange.coinsph.dto.CoinsphException;
import org.knowm.xchange.service.BaseResilientExchangeService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinsphBaseService extends BaseResilientExchangeService<CoinsphExchange> {

  protected final Coinsph coinsph;
  protected final CoinsphAuthenticated coinsphAuthenticated;
  protected final String apiKey;
  protected final ParamsDigest signatureCreator;
  protected final SynchronizedValueFactory<Long> timestampFactory;

  protected CoinsphBaseService(
      CoinsphExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
    this.coinsph = exchange.getPublicApi();
    this.coinsphAuthenticated = exchange.getAuthenticatedApi();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = exchange.getSignatureCreator();
    this.timestampFactory = exchange.getNonceFactory();
  }

  protected CoinsphException handleError(CoinsphException e) {
    // Rely on the default exception handling in Rescu which wraps it in IOException
    return e;
  }
}
