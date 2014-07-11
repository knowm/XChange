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
package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChina;
import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.service.BTCChinaDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author timmolter
 */
public class BTCChinaBasePollingService<T extends BTCChina> extends BaseExchangeService implements BasePollingService {
  
  protected final T               btcChina;
  protected final ParamsDigest    signatureCreator;
  private final Set<CurrencyPair> currencyPairs;
  
  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BTCChinaBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {
  
    super(exchangeSpecification);
    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    
    this.btcChina = RestProxyFactory.createProxy(type, (String) exchangeSpecification.getSslUri());
    this.signatureCreator = BTCChinaDigest.createInstance(exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey());
    this.currencyPairs = new HashSet<CurrencyPair>();
  }
  
  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {
  
    if (currencyPairs.isEmpty()) {
      for (String tickerKey : btcChina.getTickers("all").keySet()) {
        currencyPairs.add(BTCChinaAdapters.adaptCurrencyPairFromTickerMarketKey(tickerKey));
      }
    }
    
    return currencyPairs;
  }
  
  @SuppressWarnings("rawtypes")
  public static <T extends BTCChinaResponse> T checkResult(T returnObject) {
  
    if (returnObject.getError() != null) {
      throw new ExchangeException("Got error message: " + returnObject.getError().toString());
    }
    else if (returnObject.getResult() == null) {
      throw new ExchangeException("Null data returned");
    }
    return returnObject;
  }
}
