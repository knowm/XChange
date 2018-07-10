package org.knowm.xchange.bitmex.service;

import org.knowm.xchange.bitmex.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.HttpResponseAware;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.util.List;
import java.util.Map;

public class BitmexBaseService extends BaseExchangeService<BitmexExchange> implements BaseService {

  protected final Bitmex bitmex;
  protected final ParamsDigest signatureCreator;
  protected Integer rateLimit;
  protected Integer rateLimitRemaining;
  protected Integer rateLimitReset;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexBaseService(BitmexExchange exchange) {

    super(exchange);
    bitmex =
        RestProxyFactory.createProxy(
            BitmexAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    signatureCreator =
        BitmexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected ExchangeException handleError(BitmexException exception) {

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

  protected <T extends HttpResponseAware> T updateRateLimit(T httpResponseAware) {
    Map<String, List<String>> responseHeaders = httpResponseAware.getResponseHeaders();
    rateLimit = Integer.valueOf(responseHeaders.get("X-RateLimit-Limit").get(0));
    rateLimitRemaining = Integer.valueOf(responseHeaders.get("X-RateLimit-Remaining").get(0));
    rateLimitReset = Integer.valueOf(responseHeaders.get("X-RateLimit-Reset").get(0));

    RateLimitUpdateListener rateLimitUpdateListener = exchange.getRateLimitUpdateListener();
    if (rateLimitUpdateListener != null) {
      rateLimitUpdateListener.rateLimitUpdate(rateLimit, rateLimitRemaining, rateLimitReset);
    }
    return httpResponseAware;
  }

  public int getRateLimit() {
    return rateLimit;
  }

  public int getRateLimitRemaining() {
    return rateLimitRemaining;
  }

  public int getRateLimitReset() {
    return rateLimitReset;
  }
}
