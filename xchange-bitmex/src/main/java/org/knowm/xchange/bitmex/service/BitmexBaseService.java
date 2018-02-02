package org.knowm.xchange.bitmex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.Bitmex;
import org.knowm.xchange.bitmex.BitmexAuthenticated;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BitmexBaseService extends BaseExchangeService implements BaseService {

  protected final Bitmex bitmex;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexBaseService(Exchange exchange) {

    super(exchange);
    bitmex = RestProxyFactory.createProxy(BitmexAuthenticated.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    signatureCreator = BitmexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());

  }

  protected ExchangeException handleError(BitmexException exception) {

    if (exception.getMessage().contains("Insufficient")) {
      return new FundsExceededException(exception);
    }
    else if (exception.getMessage().contains("Rate limit exceeded")) {
      return new RateLimitExceededException(exception);
    }
    else if (exception.getMessage().contains("Internal server error")) {
      return new InternalServerException(exception);
    }
    else {
      return new ExchangeException(exception);
    }
  }
}
