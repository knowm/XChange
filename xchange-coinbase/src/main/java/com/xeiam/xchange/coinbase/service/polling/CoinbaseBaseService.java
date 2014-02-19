/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.Coinbase;
import com.xeiam.xchange.coinbase.dto.CoinbaseBaseResponse;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseToken;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseCurrency;
import com.xeiam.xchange.coinbase.service.CoinbaseDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;

/**
 * @author jamespedwards42
 */
abstract class CoinbaseBaseService<T extends Coinbase> extends BasePollingExchangeService {

  protected final T coinbase;
  protected final ParamsDigest signatureCreator;

  protected CoinbaseBaseService(Class<T> type, final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    coinbase = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    signatureCreator = CoinbaseDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  public List<CoinbaseCurrency> getCoinbaseCurrencies() throws IOException {

    return coinbase.getCurrencies();
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

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  CurrencyPair.BTC_USD

  );
  
  /**
   * Use {@link #getCoinbaseCurrencies()} instead.  It will provide
   * a list of all currencies that are currently supported by 
   * Coinbase.  
   */
  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }

  protected long getNonce() {

    return System.currentTimeMillis();
  }

  protected <R extends CoinbaseBaseResponse> R handleResponse(final R postResponse) {

    final List<String> errors = postResponse.getErrors();
    if (errors != null && !errors.isEmpty())
      throw new ExchangeException(errors.toString());

    return postResponse;
  }
}
