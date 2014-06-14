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
package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptsy.Cryptsy;
import com.xeiam.xchange.cryptsy.CryptsyAdapters;
import com.xeiam.xchange.cryptsy.CryptsyExchange;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketData;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicOrderbook;

public class CryptsyPublicMarketDataServiceRaw extends CryptsyBasePollingService<Cryptsy> {

  public CryptsyPublicMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(Cryptsy.class, exchangeSpecification);
  }

  public CryptsyPublicMarketDataServiceRaw() {

    this(CryptsyExchange.getDefaultPublicExchangeSpecification());
  }

  public Map<Integer, CryptsyPublicMarketData> getAllCryptsyMarketData() throws IOException, ExchangeException {

    return CryptsyAdapters.adaptPublicMarketDataMap(checkResult(cryptsy.getAllMarketData()).getReturnValue());
  }

  public Map<Integer, CryptsyPublicMarketData> getCryptsyMarketData(int marketId) throws IOException, ExchangeException {

    return CryptsyAdapters.adaptPublicMarketDataMap(checkResult(cryptsy.getMarketData(marketId)).getReturnValue());
  }

  public Map<Integer, CryptsyPublicOrderbook> getAllCryptsyOrderBooks() throws IOException, ExchangeException {

    return CryptsyAdapters.adaptPublicOrderBookMap(checkResult(cryptsy.getAllOrderbookData()).getReturnValue());
  }

  public Map<Integer, CryptsyPublicOrderbook> getCryptsyOrderBook(int marketId) throws IOException, ExchangeException {

    return CryptsyAdapters.adaptPublicOrderBookMap(checkResult(cryptsy.getOrderbookData(marketId)).getReturnValue());
  }
}
