package org.knowm.xchange.coinbase.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.CoinbaseAuthenticated;
import org.knowm.xchange.coinbase.dto.CoinbaseBaseResponse;
import org.knowm.xchange.coinbase.dto.account.CoinbaseToken;
import org.knowm.xchange.coinbase.dto.account.CoinbaseUser;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseCurrency;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/** @author jamespedwards42 */
public class CoinbaseBaseService extends BaseExchangeService implements BaseService {

  protected final CoinbaseAuthenticated coinbase;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected CoinbaseBaseService(Exchange exchange) {

    super(exchange);

    coinbase =
        RestProxyFactory.createProxy(
            CoinbaseAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    signatureCreator =
        CoinbaseDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
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
   * @param user New Coinbase User information.
   * @return Information for the newly created user.
   * @throws IOException
   * @see <a
   *     href="https://coinbase.com/api/doc/1.0/users/create.html">coinbase.com/api/doc/1.0/users/create.html</a>
   * @see {@link CoinbaseUser#createNewCoinbaseUser} and {@link
   *     CoinbaseUser#createCoinbaseNewUserWithReferrerId}
   */
  public CoinbaseUser createCoinbaseUser(CoinbaseUser user) throws IOException {

    final CoinbaseUser createdUser = coinbase.createUser(user);
    return handleResponse(createdUser);
  }

  /**
   * Unauthenticated resource that creates a user with an email and password.
   *
   * @param user New Coinbase User information.
   * @param oAuthClientId Optional client id that corresponds to your OAuth2 application.
   * @return Information for the newly created user, including information to perform future OAuth
   *     requests for the user.
   * @throws IOException
   * @see <a
   *     href="https://coinbase.com/api/doc/1.0/users/create.html">coinbase.com/api/doc/1.0/users/create.html</a>
   * @see {@link CoinbaseUser#createNewCoinbaseUser} and {@link
   *     CoinbaseUser#createCoinbaseNewUserWithReferrerId}
   */
  public CoinbaseUser createCoinbaseUser(CoinbaseUser user, final String oAuthClientId)
      throws IOException {

    final CoinbaseUser createdUser = coinbase.createUser(user.withoAuthClientId(oAuthClientId));
    return handleResponse(createdUser);
  }

  /**
   * Creates tokens redeemable for Bitcoin.
   *
   * @return The returned Bitcoin address can be used to send money to the token, and will be
   *     credited to the account of the token redeemer if money is sent both before or after
   *     redemption.
   * @throws IOException
   * @see <a
   *     href="https://coinbase.com/api/doc/1.0/tokens/create.html">coinbase.com/api/doc/1.0/tokens/create.html</a>
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
}
