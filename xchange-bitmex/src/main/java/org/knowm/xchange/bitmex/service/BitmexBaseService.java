package org.knowm.xchange.bitmex.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.knowm.xchange.bitmex.BitmexAuthenticated;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.RateLimitUpdateListener;
import org.knowm.xchange.bitmex.config.BitmexJacksonObjectMapperFactory;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.exceptions.SystemOverloadException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.HttpResponseAware;
import si.mazi.rescu.ParamsDigest;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BitmexBaseService extends BaseExchangeService<BitmexExchange> implements BaseService {

  protected final BitmexAuthenticated bitmex;
  protected final ParamsDigest signatureCreator;
  protected static Integer rateLimit;
  protected static Integer rateLimitRemaining;
  protected static Long rateLimitReset;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexBaseService(BitmexExchange exchange) {
    super(exchange);
    bitmex =
        ExchangeRestProxyBuilder.forInterface(
                BitmexAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BitmexJacksonObjectMapperFactory()))
            .build();
    signatureCreator =
        BitmexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected ExchangeException handleError(Exception exception) {
    if (exception != null && exception.getMessage() != null) {
      String message = exception.getMessage().toLowerCase(Locale.ROOT);
      if (message.contains("insufficient")) {
        return new FundsExceededException(exception);
      } else if (message.contains("rate limit exceeded")) {
        return new RateLimitExceededException(exception);
      } else if (message.contains("internal server error")) {
        return new InternalServerException(exception);
      } else if (message.contains("the system is currently overloaded")) {
        return new SystemOverloadException(exception);
      } else {
        return new ExchangeException(exception.getMessage(), exception);
      }
    }
    return new ExchangeException(exception);
  }

  /** see https://www.bitmex.com/app/restAPI#Request-Rate-Limits */
  protected <T extends HttpResponseAware> T updateRateLimit(Supplier<T> httpResponseAwareSupplier) {
    if (rateLimitReset != null) {
      long waitMillis = rateLimitReset * 1000 - System.currentTimeMillis();
      if (rateLimitRemaining <= 0 && waitMillis >= 0) {
        throw new ExchangeException(
            "The request is not executed due to rate limit, please wait for "
                + (waitMillis / 1000)
                + " seconds, limit:"
                + rateLimit
                + ", reset: "
                + new Date(rateLimitReset * 1000));
      } else {
        rateLimitRemaining--;
      }
    }
    HttpResponseAware responseAware = null;
    boolean rateLimitsUpdated = false;
    try {
      T result = httpResponseAwareSupplier.get();
      responseAware = result;
      return result;
    } catch (BitmexException e) {
      if (e.getHttpStatusCode() == 429) {
        // we are warned !
        try {
          Integer retryAfter = Integer.valueOf(e.getResponseHeaders().get("Retry-After").get(0));
          rateLimitRemaining = 0;
          rateLimitReset = System.currentTimeMillis() / 1000 + retryAfter;
          rateLimitsUpdated = true;
        } catch (Throwable ignored) {
        }
      } else if (e.getHttpStatusCode() == 403) {
        // we are banned now !
        rateLimitRemaining = 0;
        rateLimitReset = System.currentTimeMillis() / 1000 + 5; // lets be quiet for 5 sec
      }
      responseAware = e;
      throw handleError(e);
    } catch (Exception e) {
      throw handleError(e);
    } finally {
      if (responseAware != null && !rateLimitsUpdated) {
        Map<String, List<String>> responseHeaders = responseAware.getResponseHeaders();

        List<String> limitList = responseHeaders.get("x-ratelimit-limit");
        List<String> remainingList = responseHeaders.get("x-ratelimit-remaining");
        List<String> resetList = responseHeaders.get("x-ratelimit-reset");

        if (limitList != null
            && !limitList.isEmpty()
            && remainingList != null
            && !remainingList.isEmpty()
            && resetList != null
            && !resetList.isEmpty()) {
          rateLimit = Integer.valueOf(limitList.get(0));
          rateLimitRemaining = Integer.valueOf(remainingList.get(0));
          rateLimitReset = Long.valueOf(resetList.get(0));
          rateLimitsUpdated = true;
        }
      }
      if (rateLimitsUpdated) {
        RateLimitUpdateListener rateLimitUpdateListener = exchange.getRateLimitUpdateListener();
        if (rateLimitUpdateListener != null) {
          rateLimitUpdateListener.rateLimitUpdate(rateLimit, rateLimitRemaining, rateLimitReset);
        }
      }
    }
  }

  public int getRateLimit() {
    return rateLimit;
  }

  public int getRateLimitRemaining() {
    return rateLimitRemaining;
  }

  public long getRateLimitReset() {
    return rateLimitReset;
  }
}
