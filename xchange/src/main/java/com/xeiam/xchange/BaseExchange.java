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
package com.xeiam.xchange;

import com.xeiam.xchange.service.marketdata.MarketDataService;
import com.xeiam.xchange.service.trade.TradeService;

/**
 * <p>Abstract base class to provide the following to {@link Exchange}s:</p>
 * <ul>
 * <li>Access to common methods and fields</li>
 * </ul>
 *
 * @since 0.0.1
 */
public abstract class BaseExchange implements Exchange {

  protected MarketDataService marketDataService;
  protected TradeService accountService;

  @Override
  public MarketDataService getMarketDataService() {
    return marketDataService;
  }

  @Override
  public TradeService getTradeService() {
    return accountService;
  }

  // Package local for testing
  void setMarketDataService(MarketDataService marketDataService) {
    this.marketDataService = marketDataService;
  }

  // Package local for testing
  public void setAccountService(TradeService accountService) {
    this.accountService = accountService;
  }
}
