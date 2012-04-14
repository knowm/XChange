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

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.mtgox.v1.service.marketdata.MtGoxMarketDataService;
import com.xeiam.xchange.mtgox.v1.service.trade.MtGoxTradeService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the MtGox BTC exchange API</li>
 * </ul>
 */
public class MtGoxExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public MtGoxExchange() {
  }

  /**
   * @return A default configuration for this exchange
   */
  public static Exchange newInstance() {
    Exchange exchange = new MtGoxExchange();
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());
    return exchange;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    if (exchangeSpecification == null) {
      exchangeSpecification = getDefaultExchangeSpecification();
    }
    this.marketDataService = new MtGoxMarketDataService(exchangeSpecification);
    this.tradeService = new MtGoxTradeService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setUri("https://mtgox.com");
    exchangeSpecification.setVersion("1");
    
    return exchangeSpecification;
  }
}
