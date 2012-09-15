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

import com.xeiam.xchange.service.account.polling.PollingAccountService;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;
import com.xeiam.xchange.service.marketdata.streaming.StreamingMarketDataService;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;

/**
 * <p>
 * Abstract base class to provide the following to {@link Exchange}s:
 * </p>
 * <ul>
 * <li>Access to common methods and fields</li>
 * </ul>
 */
public abstract class BaseExchange implements Exchange {

  protected PollingMarketDataService pollingMarketDataService;
  protected PollingTradeService pollingTradeService;
  protected StreamingMarketDataService streamingMarketDataService;
  protected PollingAccountService pollingAccountService;

  @Override
  public PollingMarketDataService getPollingMarketDataService() {

    return pollingMarketDataService;
  }

  @Override
  public PollingTradeService getPollingTradeService() {

    return pollingTradeService;
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {

    return streamingMarketDataService;
  }

  @Override
  public PollingAccountService getPollingAccountService() {

    return pollingAccountService;
  }

  /* package */void setPollingMarketDataService(PollingMarketDataService marketDataService) {

    this.pollingMarketDataService = marketDataService;
  }

  /* package */void setPollingTradeService(PollingTradeService tradeService) {

    this.pollingTradeService = tradeService;
  }

  /* package */void setStreamingMarketDataService(StreamingMarketDataService streamingMarketDataService) {

    this.streamingMarketDataService = streamingMarketDataService;
  }

  /* package */void setPollingAccountService(PollingAccountService pollingAccountService) {

    this.pollingAccountService = pollingAccountService;
  }

}
