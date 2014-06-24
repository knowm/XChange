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
package com.xeiam.xchange.btctrade;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeAccountService;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeMarketDataService;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeTradeService;

public class BTCTradeExchange extends BaseExchange implements Exchange {

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BTCTradeMarketDataService(exchangeSpecification);
    if (exchangeSpecification.getApiKey() != null) {
      this.pollingAccountService = new BTCTradeAccountService(exchangeSpecification);
      this.pollingTradeService = new BTCTradeTradeService(exchangeSpecification);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(getClass());
    exchangeSpecification.setSslUri("https://www.btctrade.com/api");
    exchangeSpecification.setHost("www.btctrade.com");
    exchangeSpecification.setExchangeName("BTCTrade");
    return exchangeSpecification;
  }

}
