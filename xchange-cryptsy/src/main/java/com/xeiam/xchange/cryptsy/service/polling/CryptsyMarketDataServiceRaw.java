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

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptsy.CryptsyAuthenticated;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyGetMarketsReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyMarketTradesReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyOrderBookReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyMarketDataServiceRaw extends CryptsyBasePollingService<CryptsyAuthenticated> {

  protected static final int FULL_SIZE = 2000;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public CryptsyMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(CryptsyAuthenticated.class, exchangeSpecification);
  }

  /**
   * Get the orderbook for this market
   * 
   * @param marketID the marketID to get the orderbook for
   * @return CryptsyOrderBookReturn DTO representing the overall orderbook
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyOrderBookReturn getCryptsyOrderBook(int marketID) throws IOException, ExchangeException {

    return checkResult(cryptsy.marketorders(apiKey, signatureCreator, nextNonce(), marketID));
  }

  /**
   * @param marketID the marketID to get the orderbook for
   * @return CryptsyMarketTradesReturn DTO representing the past trades in this market
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyMarketTradesReturn getCryptsyTrades(int marketID) throws IOException, ExchangeException {

    return checkResult(cryptsy.markettrades(apiKey, signatureCreator, nextNonce(), marketID));
  }

  /**
   * Get all active markets from exchange
   * 
   * @return CryptsyTradesWrapper DTO representing market summary information
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyGetMarketsReturn getCryptsyMarkets() throws IOException, ExchangeException {

    return checkResult(cryptsy.getmarkets(apiKey, signatureCreator, nextNonce()));
  }
}
