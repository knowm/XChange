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
package com.xeiam.xchange.cryptotrade.service.polling;

import java.util.ArrayList;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptotrade.CryptoTrade;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;
import com.xeiam.xchange.cryptotrade.service.CryptoTradeHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;

public class CryptoTradeBasePollingService<T extends CryptoTrade> extends BaseExchangeService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch

  protected final String apiKey;
  protected final T cryptoTradeProxy;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public CryptoTradeBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.cryptoTradeProxy = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = CryptoTradeHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  protected int nextNonce() {

    return (int) ((System.currentTimeMillis() - START_MILLIS) / 250L);
  }

  protected <R extends CryptoTradeBaseResponse> R handleResponse(final R response) {

    final String status = response.getStatus();
    final String error = response.getError();
    if ((status != null && status.equalsIgnoreCase("error")) || (error != null && !error.isEmpty()))
      throw new ExchangeException(error);

    return response;
  }

  public static final List<CurrencyPair> CURRENCY_PAIRS = new ArrayList<CurrencyPair>();

  static {
    CURRENCY_PAIRS.add(CurrencyPair.BTC_USD);
    CURRENCY_PAIRS.add(CurrencyPair.BTC_EUR);
    CURRENCY_PAIRS.add(CurrencyPair.LTC_USD);
    CURRENCY_PAIRS.add(CurrencyPair.LTC_EUR);
    CURRENCY_PAIRS.add(CurrencyPair.LTC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.NMC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.NMC_USD);
    CURRENCY_PAIRS.add(CurrencyPair.XPM_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.XPM_USD);
    CURRENCY_PAIRS.add(CurrencyPair.XPM_PPC);
    CURRENCY_PAIRS.add(CurrencyPair.PPC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.PPC_USD);
    CURRENCY_PAIRS.add(CurrencyPair.FTC_USD);
    CURRENCY_PAIRS.add(CurrencyPair.FTC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.TRC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.DVC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.WDC_USD);
    CURRENCY_PAIRS.add(CurrencyPair.WDC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.DGC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.UTC_USD);
    CURRENCY_PAIRS.add(CurrencyPair.UTC_EUR);
    CURRENCY_PAIRS.add(CurrencyPair.UTC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.UTC_LTC);
    CURRENCY_PAIRS.add(new CurrencyPair("CINNI"));
    CURRENCY_PAIRS.add(new CurrencyPair("BC"));
    CURRENCY_PAIRS.add(new CurrencyPair("CINNI", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("BC", "BTC"));
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
