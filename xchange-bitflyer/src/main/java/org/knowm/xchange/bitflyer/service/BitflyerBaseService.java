package org.knowm.xchange.bitflyer.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.Bitflyer;
import org.knowm.xchange.bitflyer.dto.BitflyerException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BitflyerBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final Bitflyer bitflyer;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitflyerBaseService(Exchange exchange) {

    super(exchange);

    this.bitflyer =
        RestProxyFactory.createProxy(
            Bitflyer.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BitflyerDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey());
  }

  protected ExchangeException handleError(BitflyerException exception) {
    if (exception.getMessage().contains("Insufficient")) {
      return new FundsExceededException(exception);
    } else if (exception.getMessage().contains("Rate limit exceeded")) {
      return new RateLimitExceededException(exception);
    } else if (exception.getMessage().contains("Internal server error")) {
      return new InternalServerException(exception);
    } else {
      return new ExchangeException(exception);
    }
  }
}
