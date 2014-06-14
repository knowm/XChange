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
package com.xeiam.xchange.bitcoinium.service.polling;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class BitcoiniumBasePollingService extends BaseExchangeService implements BasePollingService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  new CurrencyPair(Currencies.BTC, "BITSTAMP_USD"),

  new CurrencyPair(Currencies.BTC, "BTCCHINA_CNY"),

  new CurrencyPair(Currencies.BTC, "BTCE_EUR"),

  new CurrencyPair(Currencies.BTC, "BTCE_RUR"),

  new CurrencyPair(Currencies.BTC, "BTCE_USD"),

  new CurrencyPair(Currencies.BTC, "KRAKEN_EUR"),

  new CurrencyPair(Currencies.BTC, "KRAKEN_USD")

  );

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitcoiniumBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;

  }
}
