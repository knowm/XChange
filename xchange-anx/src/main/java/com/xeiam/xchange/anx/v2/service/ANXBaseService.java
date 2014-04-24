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
package com.xeiam.xchange.anx.v2.service;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * @author timmolter
 */
public class ANXBaseService extends BaseExchangeService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  new CurrencyPair("BTC", "USD"),

  new CurrencyPair("BTC", "HKD"),

  new CurrencyPair("BTC", "EUR"),

  new CurrencyPair("BTC", "CAD"),

  new CurrencyPair("BTC", "AUD"),

  new CurrencyPair("BTC", "SGD"),

  new CurrencyPair("BTC", "JPY"),

  new CurrencyPair("BTC", "CHF"),

  new CurrencyPair("BTC", "GBP"),

  new CurrencyPair("BTC", "NZD"),

  new CurrencyPair("LTC", "BTC"),

  new CurrencyPair("LTC", "USD"),

  new CurrencyPair("LTC", "HKD"),

  new CurrencyPair("LTC", "EUR"),

  new CurrencyPair("LTC", "CAD"),

  new CurrencyPair("LTC", "AUD"),

  new CurrencyPair("LTC", "SGD"),

  new CurrencyPair("LTC", "JPY"),

  new CurrencyPair("LTC", "CHF"),

  new CurrencyPair("LTC", "GBP"),

  new CurrencyPair("LTC", "NZD"),

  new CurrencyPair("PPC", "BTC"),

  new CurrencyPair("PPC", "LTC"),

  new CurrencyPair("PPC", "USD"),

  new CurrencyPair("PPC", "HKD"),

  new CurrencyPair("PPC", "EUR"),

  new CurrencyPair("PPC", "CAD"),

  new CurrencyPair("PPC", "AUD"),

  new CurrencyPair("PPC", "SGD"),

  new CurrencyPair("PPC", "JPY"),

  new CurrencyPair("PPC", "CHF"),

  new CurrencyPair("PPC", "GBP"),

  new CurrencyPair("PPC", "NZD"),

  new CurrencyPair("NMC", "BTC"),

  new CurrencyPair("NMC", "LTC"),

  new CurrencyPair("NMC", "USD"),

  new CurrencyPair("NMC", "HKD"),

  new CurrencyPair("NMC", "EUR"),

  new CurrencyPair("NMC", "CAD"),

  new CurrencyPair("NMC", "AUD"),

  new CurrencyPair("NMC", "SGD"),

  new CurrencyPair("NMC", "JPY"),

  new CurrencyPair("NMC", "CHF"),

  new CurrencyPair("NMC", "GBP"),

  new CurrencyPair("NMC", "NZD"),

  new CurrencyPair("DOGE", "BTC"),

  new CurrencyPair("DOGE", "USD"),

  new CurrencyPair("DOGE", "HKD"),

  new CurrencyPair("DOGE", "EUR"),

  new CurrencyPair("DOGE", "CAD"),

  new CurrencyPair("DOGE", "AUD"),

  new CurrencyPair("DOGE", "SGD"),

  new CurrencyPair("DOGE", "JPY"),

  new CurrencyPair("DOGE", "CHF"),

  new CurrencyPair("DOGE", "GBP"),

  new CurrencyPair("DOGE", "NZD")

  );

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public ANXBaseService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
