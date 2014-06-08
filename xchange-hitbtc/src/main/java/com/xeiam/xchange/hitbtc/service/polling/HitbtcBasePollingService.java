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
package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.hitbtc.Hitbtc;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.service.HitbtcBaseService;
import com.xeiam.xchange.hitbtc.service.HitbtcHmacDigest;

/**
 * @author kpysniak
 */
public abstract class HitbtcBasePollingService<T extends Hitbtc> extends HitbtcBaseService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final T hitbtc;
  protected final String apiKey;
  protected final ParamsDigest signatureCreator;
  private final Set<CurrencyPair> currencyPairs;

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected HitbtcBasePollingService(Class<T> hiitbtcType, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.hitbtc = RestProxyFactory.createProxy(hiitbtcType, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    String apiKey = exchangeSpecification.getSecretKey();
    this.signatureCreator = apiKey != null && !apiKey.isEmpty() ? HitbtcHmacDigest.createInstance(apiKey) : null;
    this.currencyPairs = new HashSet<CurrencyPair>();
  }

  protected long nextNonce() {

    return lastNonce.incrementAndGet();
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty())
      currencyPairs.addAll(HitbtcAdapters.adaptCurrencyPairs(hitbtc.getSymbols()));

    return currencyPairs;
  }
}
