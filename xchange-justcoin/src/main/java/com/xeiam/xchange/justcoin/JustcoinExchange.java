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
package com.xeiam.xchange.justcoin;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.justcoin.service.polling.JustcoinAccountService;
import com.xeiam.xchange.justcoin.service.polling.JustcoinMarketDataService;
import com.xeiam.xchange.justcoin.service.polling.JustcoinTradeService;

/**
 * @author jamespedwards42
 */
public class JustcoinExchange extends BaseExchange implements Exchange {

  public JustcoinExchange() {

  }

  public static Exchange newInstance() {

    final Exchange exchange = new JustcoinExchange();
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());
    return exchange;
  }

  @Override
  public void applySpecification(final ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new JustcoinMarketDataService(exchangeSpecification);
    this.pollingAccountService = new JustcoinAccountService(exchangeSpecification);
    this.pollingTradeService = new JustcoinTradeService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    final ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://justcoin.com");
    exchangeSpecification.setHost("justcoin.com");
    exchangeSpecification.setExchangeName("Justcoin");
    exchangeSpecification.setExchangeDescription("Justcoin is a digital currency exchange, founded in 2013 with its office in Oslo, Norway.");
    exchangeSpecification.setTradeFeePercent(.5);
    return exchangeSpecification;
  }
}
