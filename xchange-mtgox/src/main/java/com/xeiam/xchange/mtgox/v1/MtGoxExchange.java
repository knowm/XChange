/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v1;

import java.util.HashMap;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.exchange.BaseExchange;
import com.xeiam.xchange.mtgox.v1.service.marketdata.MtGoxPublicHttpMarketDataService;
import com.xeiam.xchange.mtgox.v1.service.trader.MtGoxAccountService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the MtGox Bitcoin exchange API</li>
 * </ul>
 * 
 * @since 0.0.1
 */
public class MtGoxExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public MtGoxExchange() {
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    if (exchangeSpecification == null) {
      exchangeSpecification = getDefaultExchangeSpecification();
    }
    this.marketDataService = new MtGoxPublicHttpMarketDataService(exchangeSpecification);
    this.accountService = new MtGoxAccountService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    Map<String, Object> parameters = new HashMap<String, Object>();

    parameters.put(ExchangeSpecification.API_URI, "https://mtgox.com");
    parameters.put(ExchangeSpecification.API_VERSION, "1");

    return new ExchangeSpecification(this.getClass().getCanonicalName(), parameters);
  }
}
