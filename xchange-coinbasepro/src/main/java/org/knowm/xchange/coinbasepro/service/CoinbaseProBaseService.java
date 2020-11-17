package org.knowm.xchange.coinbasepro.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinbasepro.CoinbasePro;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class CoinbaseProBaseService extends BaseExchangeService implements BaseService {

  protected final CoinbasePro coinbasePro;
  protected final ParamsDigest digest;

  protected final String apiKey;
  protected final String passphrase;

  protected CoinbaseProBaseService(Exchange exchange) {

    super(exchange);
    coinbasePro =
        ExchangeRestProxyBuilder.forInterface(
                CoinbasePro.class, exchange.getExchangeSpecification())
            .build();
    digest = CoinbaseProDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    apiKey = exchange.getExchangeSpecification().getApiKey();
    passphrase =
        (String)
            exchange.getExchangeSpecification().getExchangeSpecificParametersItem("passphrase");
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
}
