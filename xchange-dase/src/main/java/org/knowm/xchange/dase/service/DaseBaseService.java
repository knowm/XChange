package org.knowm.xchange.dase.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.dase.DaseAuthenticated;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.SynchronizedValueFactory;

/** Holds shared auth state (api key, signer, timestamp factory) for DASE services. */
public class DaseBaseService extends BaseExchangeService<Exchange> implements BaseService {

  protected final String apiKey;
  protected final DaseDigest signatureCreator;
  protected final SynchronizedValueFactory<String> timestampFactory;
  protected final DaseAuthenticated daseAuth;

  public DaseBaseService(Exchange exchange) {
    super(exchange);

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    String secret = exchange.getExchangeSpecification().getSecretKey();
    if (secret == null || secret.isEmpty()) {
      this.signatureCreator = null;
    } else {
      this.signatureCreator = DaseDigest.createInstance(secret);
    }
    this.timestampFactory = new DaseTimestampFactory();

    this.daseAuth =
        ExchangeRestProxyBuilder.forInterface(
                DaseAuthenticated.class, exchange.getExchangeSpecification())
            .build();
  }

  protected void ensureCredentialsPresent() {
    if (apiKey == null || apiKey.isEmpty() || signatureCreator == null) {
      throw new ExchangeException("API credentials are not configured for Dase exchange");
    }
  }
}
