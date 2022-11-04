package org.knowm.xchange.okex.v5.service;

import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.okex.v5.Okex;
import org.knowm.xchange.okex.v5.OkexAuthenticated;
import org.knowm.xchange.okex.v5.OkexExchange;
import org.knowm.xchange.okex.v5.dto.OkexException;
import org.knowm.xchange.service.BaseResilientExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexBaseService extends BaseResilientExchangeService<OkexExchange>
    implements BaseService {

  protected final Okex okex;
  protected final OkexAuthenticated okexAuthenticated;
  protected final ParamsDigest signatureCreator;

  protected final String apiKey;
  protected final String passphrase;

  public OkexBaseService(OkexExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);

    okex =
        ExchangeRestProxyBuilder.forInterface(Okex.class, exchange.getExchangeSpecification())
            .build();
    okexAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                OkexAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    signatureCreator =
        OkexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    apiKey = exchange.getExchangeSpecification().getApiKey();
    passphrase =
        (String)
            exchange.getExchangeSpecification().getExchangeSpecificParametersItem("passphrase");
  }

  /** https://www.okex.com/docs-v5/en/#error-code * */
  protected ExchangeException handleError(OkexException exception) {
    if (exception.getMessage().contains("Requests too frequent")) {
      return new RateLimitExceededException(exception);
    } else if (exception.getMessage().contains("System error")) {
      return new InternalServerException(exception);
    } else {
      return new ExchangeException(exception);
    }
  }
}
