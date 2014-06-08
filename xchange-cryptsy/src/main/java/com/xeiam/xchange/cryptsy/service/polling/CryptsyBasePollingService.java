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
 * THE SOFTWARE IS PROVIdED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptsy.Cryptsy;
import com.xeiam.xchange.cryptsy.CryptsyAdapters;
import com.xeiam.xchange.cryptsy.CryptsyCurrencyUtils;
import com.xeiam.xchange.cryptsy.dto.CryptsyGenericReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketData;
import com.xeiam.xchange.cryptsy.service.CryptsyBaseService;
import com.xeiam.xchange.cryptsy.service.CryptsyHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * @author ObsessiveOrange
 */
public class CryptsyBasePollingService<T extends Cryptsy> extends CryptsyBaseService {

  private final Logger logger = LoggerFactory.getLogger(CryptsyBasePollingService.class);

  public final Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  // counter for the nonce
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final String apiKey;
  protected final T cryptsy;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public CryptsyBasePollingService(Class<T> cryptsyType, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.cryptsy = RestProxyFactory.createProxy(cryptsyType, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = CryptsyHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty()) {
      updateExchangeSymbols();
    }
    return currencyPairs;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void updateExchangeSymbols() throws ExchangeException, IOException {

    Map<Integer, CryptsyPublicMarketData> overallMarketData = new CryptsyPublicMarketDataServiceRaw().getAllCryptsyMarketData();

    currencyPairs.addAll(CryptsyAdapters.adaptCurrencyPairs(overallMarketData));

    // Map of market currencyPairs and marketIds also have to be updated.
    HashMap[] marketSets = CryptsyAdapters.adaptMarketSets(overallMarketData);
    CryptsyCurrencyUtils.marketIds_CurrencyPairs = marketSets[0];
    CryptsyCurrencyUtils.currencyPairs_MarketIds = marketSets[1];
  }

  protected int nextNonce() {

    int nextNonce = lastNonce.incrementAndGet();
    logger.debug("nextNonce in CryptsyBaseService: " + nextNonce);

    return nextNonce;
  }

  @SuppressWarnings("rawtypes")
  public static <T extends CryptsyGenericReturn> T checkResult(T info) {

    if (!info.isSuccess()) {
      throw new ExchangeException("Cryptsy returned an error: " + info.getError());
    }
    else if (info.getError() != null) {
      throw new ExchangeException("Got error message: " + info.getError());
    }
    return info;
  }

}
