package org.knowm.xchange.bitrue.service;

import org.knowm.xchange.bitrue.BitrueAuthenticated;
import org.knowm.xchange.bitrue.BitrueExchange;

import org.knowm.xchange.bitrue.dto.meta.exchangeinfo.BitrueExchangeInfo;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.service.BaseResilientExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;

import static org.knowm.xchange.bitrue.BitrueResilience.REQUEST_WEIGHT_RATE_LIMITER;

public class BitrueBaseService extends BaseResilientExchangeService<BitrueExchange> {

  protected final Logger LOG = LoggerFactory.getLogger(getClass());

  protected final String apiKey;
  protected final BitrueAuthenticated bitrue;
  protected final ParamsDigest signatureCreator;

  protected BitrueBaseService(
      BitrueExchange exchange,
      BitrueAuthenticated bitrue,
      ResilienceRegistries resilienceRegistries) {

    super(exchange, resilienceRegistries);
    this.bitrue = bitrue;
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BitrueHmacDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public Long getRecvWindow() {
    Object obj =
        exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    if (obj == null) return null;
    if (obj instanceof Number) {
      long value = ((Number) obj).longValue();
      if (value < 0 || value > 60000) {
        throw new IllegalArgumentException(
            "Exchange-specific parameter \"recvWindow\" must be in the range [0, 60000].");
      }
      return value;
    }
    if (obj.getClass().equals(String.class)) {
      try {
        return Long.parseLong((String) obj, 10);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(
            "Exchange-specific parameter \"recvWindow\" could not be parsed.", e);
      }
    }
    throw new IllegalArgumentException(
        "Exchange-specific parameter \"recvWindow\" could not be parsed.");
  }

  public SynchronizedValueFactory<Long> getTimestampFactory() {
    return exchange.getTimestampFactory();
  }

  public BitrueExchangeInfo getExchangeInfo() throws IOException {
    return decorateApiCall(bitrue::exchangeInfo)
        .withRetry(retry("exchangeInfo"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

}
