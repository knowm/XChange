package com.xeiam.xchange.coinbase.service.polling;

import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.CoinbaseAuthenticated;
import com.xeiam.xchange.coinbase.dto.CoinbaseBaseResponse;
import com.xeiam.xchange.coinbase.service.CoinbaseDigest;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;

public abstract class CoinbaseBaseAuthenticatedService extends BasePollingExchangeService {

  protected final CoinbaseAuthenticated coinbaseAuthenticated;
  protected final ParamsDigest signatureCreator;

  protected CoinbaseBaseAuthenticatedService(final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    coinbaseAuthenticated = RestProxyFactory.createProxy(CoinbaseAuthenticated.class, exchangeSpecification.getSslUri());
    signatureCreator = CoinbaseDigest.createInstance(exchangeSpecification.getSecretKey());
  }
  
  protected <T extends CoinbaseBaseResponse> T handleResponse(final T postResponse) {

    final List<String> errors = postResponse.getErrors();
    if (errors != null && !errors.isEmpty())
      throw new ExchangeException(errors.toString());

    return postResponse;
  }
}
