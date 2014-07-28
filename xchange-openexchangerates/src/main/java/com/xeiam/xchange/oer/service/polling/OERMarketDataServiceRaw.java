/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.oer.service.polling;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.CachedDataSession;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.oer.OER;
import com.xeiam.xchange.oer.OERUtils;
import com.xeiam.xchange.oer.dto.marketdata.OERRates;
import com.xeiam.xchange.oer.dto.marketdata.OERTickers;

/**
 * @author timmolter
 */
public class OERMarketDataServiceRaw extends OERBasePollingService implements CachedDataSession {

  private final Logger logger = LoggerFactory.getLogger(OERMarketDataServiceRaw.class);

  private final OER openExchangeRates;

  /**
   * Time stamps used to pace API calls
   */
  private long tickerRequestTimeStamp = 0L;

  OERTickers cachedOERTickers;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public OERMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.openExchangeRates = RestProxyFactory.createProxy(OER.class, exchangeSpecification.getPlainTextUri());
  }

  @Override
  public long getRefreshRate() {

    return OERUtils.REFRESH_RATE_MILLIS;
  }

  public OERRates getOERTicker() throws IOException {

    // check for pacing violation
    if (tickerRequestTimeStamp == 0L || System.currentTimeMillis() - tickerRequestTimeStamp >= getRefreshRate()) {

      logger.debug("requesting OER tickers");

      // Request data
      cachedOERTickers = openExchangeRates.getTickers(exchangeSpecification.getApiKey());
      if (cachedOERTickers == null) {
        throw new ExchangeException("Null response returned from Open Exchange Rates!");
      }

      // if we make it here, set the timestamp so we know a cached request has been successful
      tickerRequestTimeStamp = System.currentTimeMillis();
    }

    OERRates rates = cachedOERTickers.getRates();

    // Adapt to XChange DTOs
    return rates;
  }

}
