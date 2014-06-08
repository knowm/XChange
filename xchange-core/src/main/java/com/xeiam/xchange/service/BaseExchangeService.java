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
package com.xeiam.xchange.service;

import java.io.IOException;
import java.util.Collection;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Abstract base class to provide the following to exchange services:
 * </p>
 * <ul>
 * <li>Provision of standard specification parsing</li>
 * </ul>
 */
public abstract class BaseExchangeService {

  /**
   * The exchange specification containing session-specific information
   */
  protected final ExchangeSpecification exchangeSpecification;

  /**
   * Constructor Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  protected BaseExchangeService(ExchangeSpecification exchangeSpecification) {

    Assert.notNull(exchangeSpecification, "exchangeSpecification cannot be null");

    this.exchangeSpecification = exchangeSpecification;
  }

  /**
   * <p>
   * what symbol pairs the exchange supports
   * </p>
   * 
   * @return The symbol pairs supported by this exchange (e.g. EUR/USD), null if some sort of error occurred. Implementers should log the error.
   */
  public abstract Collection<CurrencyPair> getExchangeSymbols() throws IOException;

}
