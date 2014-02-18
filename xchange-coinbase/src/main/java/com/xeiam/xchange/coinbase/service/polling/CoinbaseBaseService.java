package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.Coinbase;
import com.xeiam.xchange.coinbase.dto.CoinbaseBaseResponse;
import com.xeiam.xchange.coinbase.dto.CoinbaseToken;
import com.xeiam.xchange.coinbase.dto.CoinbaseUser;
import com.xeiam.xchange.coinbase.service.CoinbaseDigest;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;

public abstract class CoinbaseBaseService<T extends Coinbase> extends BasePollingExchangeService {

  protected final T coinbase;
  protected final ParamsDigest signatureCreator;

  protected CoinbaseBaseService(Class<T> type, final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    coinbase = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    signatureCreator = CoinbaseDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  protected <R extends CoinbaseBaseResponse> R handleResponse(final R postResponse) {

    final List<String> errors = postResponse.getErrors();
    if (errors != null && !errors.isEmpty())
      throw new ExchangeException(errors.toString());

    return postResponse;
  }

  public CoinbaseUser createCoinbaseUser(final CoinbaseUser user) throws IOException {

    final CoinbaseUser createdUser = coinbase.createUser(user);
    return handleResponse(createdUser);
  }

  public CoinbaseUser createCoinbaseUser(final CoinbaseUser user, final String oAuthClientId) throws IOException {

    final CoinbaseUser createdUser = coinbase.createUser(user.withoAuthClientId(oAuthClientId));
    return handleResponse(createdUser);
  }

  public CoinbaseToken createCoinbaseToken() throws IOException {

    final CoinbaseToken token = coinbase.createToken();
    return handleResponse(token);
  }
}
