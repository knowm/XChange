package org.knowm.xchange.bitcoinde.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.Bitcoinde;
import org.knowm.xchange.bitcoinde.dto.BitcoindeException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class BitcoindeBaseService extends BaseExchangeService implements BaseService {

  protected final Bitcoinde bitcoinde;
  protected final String apiKey;
  protected final BitcoindeDigest signatureCreator;

  /** Constructor */
  protected BitcoindeBaseService(Exchange exchange) {

    super(exchange);
    this.bitcoinde =
        RestProxyFactory.createProxy(
            Bitcoinde.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BitcoindeDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), apiKey);
  }

  protected RuntimeException handleError(BitcoindeException exception) {

    if (exception.getMessage().contains("Insufficient credits")) {
      return new RateLimitExceededException(exception);
    } else {
      return new ExchangeException(exception.getMessage(), exception);
    }
  }
}
