package org.knowm.xchange.bibox.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.BiboxAuthenticated;
import org.knowm.xchange.bibox.dto.BiboxResponse;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class BiboxBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final BiboxAuthenticated bibox;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BiboxBaseService(Exchange exchange) {
    super(exchange);
    this.bibox =
        ExchangeRestProxyBuilder.forInterface(
                BiboxAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BiboxDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected static void throwErrors(BiboxResponse<?> response) {
    if (response.getError() != null) {
      throw new ExchangeException(
          response.getError().getCode() + ": " + response.getError().getMsg());
    }
  }
}
