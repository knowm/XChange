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
package com.xeiam.xchange.mtgox.v0.service.polling.marketdata;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;

/**
 * @author timmolter
 */
@Deprecated
public class MtGoxBasePollingService extends BasePollingExchangeService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  CurrencyPair.BTC_USD,

  CurrencyPair.BTC_EUR,

  CurrencyPair.BTC_GBP,

  CurrencyPair.BTC_AUD,

  CurrencyPair.BTC_CAD,

  CurrencyPair.BTC_CHF,

  CurrencyPair.BTC_JPY,

  CurrencyPair.BTC_CNY,

  CurrencyPair.BTC_DKK,

  CurrencyPair.BTC_HKD,

  CurrencyPair.BTC_NZD,

  CurrencyPair.BTC_PLN,

  CurrencyPair.BTC_RUB,

  CurrencyPair.BTC_SEK,

  CurrencyPair.BTC_SGD,

  CurrencyPair.BTC_THB,

  CurrencyPair.BTC_NOK

  );

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public MtGoxBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
