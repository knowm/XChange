package org.knowm.xchange.gemini.v1.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.gemini.v1.GeminiAuthenticated;
import org.knowm.xchange.gemini.v1.service.GeminiHmacPostBodyDigest;
import org.knowm.xchange.gemini.v1.service.GeminiPayloadDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class GeminiBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final String apiKey;
  protected final GeminiAuthenticated Gemini;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest payloadCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public GeminiBasePollingService(Exchange exchange) {

    super(exchange);

    this.Gemini = RestProxyFactory.createProxy(GeminiAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = GeminiHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.payloadCreator = new GeminiPayloadDigest();
  }

}
