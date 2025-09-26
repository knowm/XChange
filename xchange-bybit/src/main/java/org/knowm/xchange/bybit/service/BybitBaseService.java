package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitResilience.BATCH_ORDER_AMEND_LINEAR_AND_INVERSE_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.BATCH_ORDER_AMEND_OPTION_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.BATCH_ORDER_AMEND_SPOT_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_AMEND_LINEAR_AND_INVERSE_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_AMEND_OPTION_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_AMEND_SPOT_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_CANCEL_LINEAR_AND_INVERSE_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_CANCEL_OPTION_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_CANCEL_SPOT_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_CREATE_LINEAR_AND_INVERSE_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_CREATE_OPTION_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_CREATE_SPOT_RATE_LIMITER;

import io.github.resilience4j.ratelimiter.RateLimiter;
import org.knowm.xchange.bybit.Bybit;
import org.knowm.xchange.bybit.BybitAuthenticated;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.service.BaseResilientExchangeService;
import si.mazi.rescu.ParamsDigest;

public class BybitBaseService extends BaseResilientExchangeService<BybitExchange> {

  protected final BybitAuthenticated bybitAuthenticated;
  protected final Bybit bybit;
  protected final ParamsDigest signatureCreator;

  protected final String apiKey;

  protected BybitBaseService(BybitExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
    bybit =
        ExchangeRestProxyBuilder.forInterface(Bybit.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BybitJacksonObjectMapperFactory()))
            .build();

    bybitAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BybitAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BybitJacksonObjectMapperFactory()))
            .build();

    signatureCreator =
        BybitDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    apiKey = exchange.getExchangeSpecification().getApiKey();
  }

  public RateLimiter getCancelOrderRateLimiter(BybitCategory category) {
    switch (category) {
      case LINEAR:
      case INVERSE:
        return rateLimiter(ORDER_CANCEL_LINEAR_AND_INVERSE_RATE_LIMITER);
      case SPOT:
        return rateLimiter(ORDER_CANCEL_SPOT_RATE_LIMITER);
      case OPTION:
        return rateLimiter(ORDER_CANCEL_OPTION_LIMITER);
    }
    return null;
  }

  public RateLimiter getCreateOrderRateLimiter(BybitCategory category) {
    switch (category) {
      case LINEAR:
      case INVERSE:
        return rateLimiter(ORDER_CREATE_LINEAR_AND_INVERSE_RATE_LIMITER);
      case SPOT:
        return rateLimiter(ORDER_CREATE_SPOT_RATE_LIMITER);
      case OPTION:
        return rateLimiter(ORDER_CREATE_OPTION_LIMITER);
    }
    return null;
  }

  public RateLimiter getAmendOrderRateLimiter(BybitCategory category) {
    switch (category) {
      case LINEAR:
      case INVERSE:
        return rateLimiter(ORDER_AMEND_LINEAR_AND_INVERSE_RATE_LIMITER);
      case SPOT:
        return rateLimiter(ORDER_AMEND_SPOT_RATE_LIMITER);
      case OPTION:
        return rateLimiter(ORDER_AMEND_OPTION_LIMITER);
    }
    return null;
  }

  public RateLimiter getBatchAmendOrderRateLimiter(BybitCategory category) {
    switch (category) {
      case LINEAR:
      case INVERSE:
        return rateLimiter(BATCH_ORDER_AMEND_LINEAR_AND_INVERSE_RATE_LIMITER);
      case SPOT:
        return rateLimiter(BATCH_ORDER_AMEND_SPOT_RATE_LIMITER);
      case OPTION:
        return rateLimiter(BATCH_ORDER_AMEND_OPTION_LIMITER);
    }
    return null;
  }
}
