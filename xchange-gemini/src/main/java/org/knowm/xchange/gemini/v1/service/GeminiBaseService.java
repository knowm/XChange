package org.knowm.xchange.gemini.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.gemini.v1.GeminiAuthenticated;
import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v2.Gemini2;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class GeminiBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final GeminiAuthenticated gemini;
  protected final Gemini2 gemini2;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest payloadCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public GeminiBaseService(Exchange exchange) {

    super(exchange);

    this.gemini =
        RestProxyFactory.createProxy(
            GeminiAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    this.gemini2 =
        RestProxyFactory.createProxy(
            Gemini2.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        GeminiHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.payloadCreator = new GeminiPayloadDigest();
  }

  protected ExchangeException handleException(GeminiException e) {
    if (e.getMessage().contains("due to insufficient funds")
        || e.getMessage().contains("you do not have enough available"))
      return new FundsExceededException(e);

    return new ExchangeException(e);
  }
}
