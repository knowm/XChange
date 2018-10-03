package org.knowm.xchange.bitfinex.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.common.dto.BitfinexException;
import org.knowm.xchange.bitfinex.common.service.BitfinexHmacPostBodyDigest;
import org.knowm.xchange.bitfinex.common.service.BitfinexPayloadDigest;
import org.knowm.xchange.bitfinex.v1.BitfinexAuthenticated;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BitfinexBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final BitfinexAuthenticated bitfinex;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest payloadCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexBaseService(Exchange exchange) {

    super(exchange);

    this.bitfinex =
        RestProxyFactory.createProxy(
            BitfinexAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BitfinexHmacPostBodyDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey());
    this.payloadCreator = new BitfinexPayloadDigest();
  }

  public ExchangeException handleException(BitfinexException exception) {

    if (exception.getMessage() != null) {
      if (exception.getMessage().toLowerCase().contains("nonce")) {
        return new NonceException(exception.getMessage());
      } else if (exception.getMessage().toLowerCase().contains("not enough exchange balance")) {
        return new FundsExceededException(exception);
      } else if (exception.getMessage().toUpperCase().contains("ERR_RATE_LIMIT")) {
        return new RateLimitExceededException(exception);
      }
    }

    return new ExchangeException(exception);
  }
}
