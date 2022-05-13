package org.knowm.xchange.bitrue.service;

import org.knowm.xchange.bitrue.BitrueAuthenticated;
import org.knowm.xchange.bitrue.BitrueExchange;
import org.knowm.xchange.bitrue.dto.BitrueException;
import org.knowm.xchange.bitrue.dto.account.*;
import org.knowm.xchange.client.ResilienceRegistries;

import java.io.IOException;
import java.util.Map;

import static org.knowm.xchange.bitrue.BitrueResilience.REQUEST_WEIGHT_RATE_LIMITER;

public class BitrueAccountServiceRaw extends BitrueBaseService {

  public BitrueAccountServiceRaw(
      BitrueExchange exchange,
      BitrueAuthenticated binance,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, binance, resilienceRegistries);
  }

  public BitrueAccountInformation account() throws BitrueException, IOException {
    return decorateApiCall(
            () -> bitrue.account(getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
        .withRetry(retry("account"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 5)
        .call();
  }



  public Map<String, AssetDetail> requestAssetDetail() throws IOException {
    return decorateApiCall(
            () ->
                bitrue.assetDetail(
                    getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
        .withRetry(retry("assetDetail"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }


}
