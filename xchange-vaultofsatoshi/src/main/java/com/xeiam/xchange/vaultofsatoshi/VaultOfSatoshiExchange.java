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
package com.xeiam.xchange.vaultofsatoshi;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.vaultofsatoshi.service.polling.VaultOfSatoshiMarketDataService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the VaultOfSatoshi exchange API</li>
 * </ul>
 */
public class VaultOfSatoshiExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public VaultOfSatoshiExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new VaultOfSatoshiMarketDataService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.vaultofsatoshi.com");
    exchangeSpecification.setHost("api.vaultofsatoshi.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("VaultOfSatoshi");
    exchangeSpecification.setExchangeDescription("Vault of Satoshi is a reputable 100% Canadian owned and operated virtual currency trading platform serving a Global market.");

    return exchangeSpecification;
  }
}
