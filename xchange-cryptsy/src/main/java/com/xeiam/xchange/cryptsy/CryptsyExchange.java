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
package com.xeiam.xchange.cryptsy;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyAccountService;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyMarketDataService;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyPublicMarketDataService;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyTradeService;

/**
 * @author ObsessiveOrange
 */
public class CryptsyExchange extends BaseExchange implements Exchange {

  private CryptsyPublicMarketDataService pollingPublicMarketDataService;

  /**
   * Default constructor for ExchangeFactory
   */
  public CryptsyExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new CryptsyMarketDataService(exchangeSpecification);
    this.pollingAccountService = new CryptsyAccountService(exchangeSpecification);
    this.pollingTradeService = new CryptsyTradeService(exchangeSpecification);

    this.pollingPublicMarketDataService = new CryptsyPublicMarketDataService();
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.cryptsy.com");
    exchangeSpecification.setHost("api.cryptsy.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cryptsy");
    exchangeSpecification.setExchangeDescription("Cryptsy is an altcoin exchange");

    return exchangeSpecification;
  }

  public void applyPublicSpecification(ExchangeSpecification exchangeSpecification) {

    this.pollingPublicMarketDataService = new CryptsyPublicMarketDataService(exchangeSpecification);
  }

  public static ExchangeSpecification getDefaultPublicExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(CryptsyExchange.class.getCanonicalName());
    exchangeSpecification.setSslUri("http://pubapi.cryptsy.com");
    exchangeSpecification.setHost("pubapi.cryptsy.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cryptsy");
    exchangeSpecification.setExchangeDescription("Cryptsy is an altcoin exchange");

    return exchangeSpecification;
  }

  public CryptsyPublicMarketDataService getPublicPollingMarketDataService() {

    return pollingPublicMarketDataService;
  }
}
