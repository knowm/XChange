package org.knowm.xchange.coindcx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindcx.Coindcx;
import org.knowm.xchange.coindcx.dto.CoindcxException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class CoindcxBaseService extends BaseExchangeService implements BaseService {

  protected final Coindcx coindcx;
  protected final String apiKey;
  protected final ParamsDigest signatureCreator;

    protected CoindcxBaseService(Exchange exchange) {
    super(exchange);
    coindcx =
        RestProxyFactory.createProxy(
            Coindcx.class, exchange.getExchangeSpecification().getSslUri());

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        CoindcxHmacPostBodyDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey());
  }

  protected ExchangeException handleException(CoindcxException e) {
    if (e.getMessage().contains("due to insufficient funds")
        || e.getMessage().contains("you do not have enough available"))
      return new FundsExceededException(e);

    return new ExchangeException(e);
  }
}
