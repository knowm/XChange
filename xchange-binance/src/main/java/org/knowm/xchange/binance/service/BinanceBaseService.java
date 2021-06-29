package org.knowm.xchange.binance.service;

import static org.knowm.xchange.binance.BinanceResilience.REQUEST_WEIGHT_RATE_LIMITER;

import java.io.IOException;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.meta.BinanceSystemStatus;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.service.BaseResilientExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceBaseService extends BaseResilientExchangeService<BinanceExchange> {

  protected final Logger LOG = LoggerFactory.getLogger(getClass());

  protected final String apiKey;
  protected final BinanceAuthenticated binance;
  protected final ParamsDigest signatureCreator;

  protected BinanceBaseService(
      BinanceExchange exchange,
      BinanceAuthenticated binance,
      ResilienceRegistries resilienceRegistries) {

    super(exchange, resilienceRegistries);
    this.binance = binance;
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BinanceHmacDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
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

  public BinanceExchangeInfo getExchangeInfo() throws IOException {
    return decorateApiCall(binance::exchangeInfo)
        .withRetry(retry("exchangeInfo"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public BinanceSystemStatus getSystemStatus() throws IOException {
    return decorateApiCall(binance::systemStatus).call();
  }
}
