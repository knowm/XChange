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
package com.xeiam.xchange.btce.v2.service.polling;

import java.util.Arrays;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v2.BTCEAuthenticated;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCEReturn;
import com.xeiam.xchange.btce.v2.service.BTCEHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;

/**
 * @author Matija Mazi
 */
@Deprecated
public class BTCEBasePollingService extends BasePollingExchangeService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch

  protected final String apiKey;
  protected final BTCEAuthenticated btce;
  protected final ParamsDigest signatureCreator;

  // counter for the nonce
  private static int lastNonce = -1;

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  CurrencyPair.BTC_USD,

  CurrencyPair.BTC_RUR,

  CurrencyPair.BTC_EUR,

  CurrencyPair.LTC_BTC,

  CurrencyPair.LTC_USD,

  CurrencyPair.LTC_RUR,

  CurrencyPair.LTC_EUR,

  CurrencyPair.NMC_BTC,

  CurrencyPair.NMC_USD,

  CurrencyPair.USD_RUR,

  CurrencyPair.EUR_USD,

  CurrencyPair.EUR_RUR,

  CurrencyPair.NVC_BTC,

  CurrencyPair.NVC_USD,

  CurrencyPair.TRC_BTC,

  CurrencyPair.PPC_BTC,

  CurrencyPair.PPC_USD,

  CurrencyPair.FTC_BTC,

  CurrencyPair.XPM_BTC

  );

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCEBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.btce = RestProxyFactory.createProxy(BTCEAuthenticated.class, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = BTCEHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  protected synchronized int nextNonce() {

    // nonce logic is now more robust.
    // on the first call, it initializes to a number based upon the quarter second. From then on, it increments it.
    //
    // As long as you do not create a new BTCEBasedPollingService more than once every quarter second and make sure
    // that you throw away the old one before you make a new one, this should work out fine.
    if (lastNonce < 0) {
      lastNonce = (int) ((System.currentTimeMillis() - START_MILLIS) / 250L);
    }
    int nonce = lastNonce++;
    return nonce;
  }

  protected void checkResult(BTCEReturn<?> info) {

    if (!info.isSuccess()) {
      throw new ExchangeException("BTCE returned an error: " + info.getError());
    }
    else if (info.getReturnValue() == null) {
      throw new ExchangeException("Didn't recieve any return value. Message: " + info.getError());
    }
    else if (info.getError() != null) {
      throw new ExchangeException("Got error message: " + info.getError());
    }
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
