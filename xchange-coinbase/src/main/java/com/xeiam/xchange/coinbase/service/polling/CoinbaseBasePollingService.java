package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbase.CoinbaseAuthenticated;
import com.xeiam.xchange.coinbase.dto.CoinbaseBaseResponse;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseToken;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseCurrency;
import com.xeiam.xchange.coinbase.service.CoinbaseDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author jamespedwards42
 */
public class CoinbaseBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final CoinbaseAuthenticated coinbase;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected CoinbaseBasePollingService(Exchange exchange) {

    super(exchange);

    coinbase = RestProxyFactory.createProxy(CoinbaseAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    signatureCreator = CoinbaseDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  /**
   * Unauthenticated resource that returns currencies supported on Coinbase.
   *
   * @return A list of currency names and their corresponding ISO code.
   * @throws IOException
   */
  public List<CoinbaseCurrency> getCoinbaseCurrencies() throws IOException {

    return coinbase.getCurrencies();
  }

  /**
   * Unauthenticated resource that creates a user with an email and password.
   *
   * @see <a href="https://coinbase.com/api/doc/1.0/users/create.html">coinbase.com/api/doc/1.0/users/create.html</a>
   * @see {@link CoinbaseUser#createNewCoinbaseUser} and {@link CoinbaseUser#createCoinbaseNewUserWithReferrerId}
   * @param user New Coinbase User information.
   * @return Information for the newly created user.
   * @throws IOException
   */
  public CoinbaseUser createCoinbaseUser(CoinbaseUser user) throws IOException {

    final CoinbaseUser createdUser = coinbase.createUser(user);
    return handleResponse(createdUser);
  }

  /**
   * Unauthenticated resource that creates a user with an email and password.
   *
   * @see <a href="https://coinbase.com/api/doc/1.0/users/create.html">coinbase.com/api/doc/1.0/users/create.html</a>
   * @see {@link CoinbaseUser#createNewCoinbaseUser} and {@link CoinbaseUser#createCoinbaseNewUserWithReferrerId}
   * @param user New Coinbase User information.
   * @param oAuthClientId Optional client id that corresponds to your OAuth2 application.
   * @return Information for the newly created user, including information to perform future OAuth requests for the user.
   * @throws IOException
   */
  public CoinbaseUser createCoinbaseUser(CoinbaseUser user, final String oAuthClientId) throws IOException {

    final CoinbaseUser createdUser = coinbase.createUser(user.withoAuthClientId(oAuthClientId));
    return handleResponse(createdUser);
  }

  /**
   * Creates tokens redeemable for Bitcoin.
   *
   * @see <a href="https://coinbase.com/api/doc/1.0/tokens/create.html">coinbase.com/api/doc/1.0/tokens/create.html</a>
   * @return The returned Bitcoin address can be used to send money to the token, and will be credited to the account of the token redeemer if money
   *         is sent both before or after redemption.
   * @throws IOException
   */
  public CoinbaseToken createCoinbaseToken() throws IOException {

    final CoinbaseToken token = coinbase.createToken();
    return handleResponse(token);
  }

  protected <R extends CoinbaseBaseResponse> R handleResponse(R response) {

    final List<String> errors = response.getErrors();
    if (errors != null && !errors.isEmpty()) {
      throw new ExchangeException(errors.toString());
    }

    return response;
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
