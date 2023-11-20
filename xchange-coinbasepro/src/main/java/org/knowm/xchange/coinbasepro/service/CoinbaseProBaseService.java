package org.knowm.xchange.coinbasepro.service;

import static org.knowm.xchange.coinbasepro.CoinbaseProResilience.PRIVATE_REST_ENDPOINT_RATE_LIMITER;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import lombok.SneakyThrows;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ProxyConfig;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinbasepro.CoinbasePro;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProWebsocketAuthData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.BaseResilientExchangeService;
import org.knowm.xchange.utils.timestamp.UnixTimestampFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class CoinbaseProBaseService extends BaseResilientExchangeService<CoinbaseProExchange> {

  protected final CoinbasePro coinbasePro;
  protected final ParamsDigest digest;

  protected final String apiKey;
  protected final String passphrase;

  @SneakyThrows
  protected CoinbaseProBaseService(
      CoinbaseProExchange exchange, ResilienceRegistries resilienceRegistries) {

    super(exchange, resilienceRegistries);
    coinbasePro = getCoinbaseProExchangeRestProxy();
    digest = CoinbaseProDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    apiKey = exchange.getExchangeSpecification().getApiKey();
    passphrase =
        (String)
            exchange.getExchangeSpecification().getExchangeSpecificParametersItem("passphrase");
  }
  public CoinbasePro getCoinbaseProExchangeRestProxy() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    return ExchangeRestProxyBuilder.forInterface(
            CoinbasePro.class, exchange.getExchangeSpecification())
        .restProxyFactory(ProxyConfig.getInstance().getRestProxyFactoryClass().getDeclaredConstructor().newInstance())
        .build();
  }
  protected ExchangeException handleError(CoinbaseProException exception) {

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

  public CoinbaseProWebsocketAuthData getWebsocketAuthData()
      throws CoinbaseProException, IOException {
    long timestamp = UnixTimestampFactory.INSTANCE.createValue();
    WebhookAuthDataParamsDigestProxy digestProxy = new WebhookAuthDataParamsDigestProxy();
    JsonNode json =
        decorateApiCall(() -> coinbasePro.getVerifyId(apiKey, digestProxy, timestamp, passphrase))
            .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
            .call();
    String userId = json.get("id").asText();
    return new CoinbaseProWebsocketAuthData(
        userId, apiKey, passphrase, digestProxy.getSignature(), timestamp);
  }

  private class WebhookAuthDataParamsDigestProxy implements ParamsDigest {
    private String signature;

    @Override
    public String digestParams(RestInvocation restInvocation) {
      signature = digest.digestParams(restInvocation);
      return signature;
    }

    public String getSignature() {
      return signature;
    }
  }
}
