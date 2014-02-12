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
package com.xeiam.xchange;

import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * <p>
 * Abstract base class to provide the following to {@link Exchange}s:
 * </p>
 * <ul>
 * <li>Access to common methods and fields</li>
 * </ul>
 */
public abstract class BaseExchange implements Exchange {

  protected ExchangeSpecification exchangeSpecification;

  protected PollingMarketDataService pollingMarketDataService;
  protected PollingTradeService pollingTradeService;
  protected PollingAccountService pollingAccountService;
  protected StreamingExchangeService streamingExchangeService;

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    ExchangeSpecification defaultSpecification = getDefaultExchangeSpecification();

    // Check if default is for everything
    if (exchangeSpecification == null) {
      this.exchangeSpecification = defaultSpecification;
    }
    else {
      // Using a configured exchange
      if (exchangeSpecification.getExchangeName() == null) {
        exchangeSpecification.setExchangeName(defaultSpecification.getExchangeName());
      }
      if (exchangeSpecification.getExchangeDescription() == null) {
        exchangeSpecification.setExchangeDescription(defaultSpecification.getExchangeDescription());
      }
      if (exchangeSpecification.getSslUri() == null) {
        exchangeSpecification.setSslUri(defaultSpecification.getSslUri());
      }
      if (exchangeSpecification.getHost() == null) {
        exchangeSpecification.setHost(defaultSpecification.getHost());
      }
      this.exchangeSpecification = exchangeSpecification;
    }

  }

  @Override
  public ExchangeSpecification getExchangeSpecification() {

    return exchangeSpecification;
  }

  @Override
  public PollingMarketDataService getPollingMarketDataService() {

    return pollingMarketDataService;
  }

  @Override
  public PollingTradeService getPollingTradeService() {

    return pollingTradeService;
  }

  @Override
  public PollingAccountService getPollingAccountService() {

    return pollingAccountService;
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {

    return streamingExchangeService;
  }

}
