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
package com.xeiam.xchange.kraken.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.utils.Assert;

public class KrakenBaseService extends BaseExchangeService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = new ArrayList<CurrencyPair>(Arrays.asList(CurrencyPair.LTC_XRP, CurrencyPair.LTC_EUR, CurrencyPair.LTC_USD, CurrencyPair.LTC_KRW,
      CurrencyPair.BTC_LTC, CurrencyPair.BTC_NMC, CurrencyPair.BTC_XRP, CurrencyPair.BTC_XVN, CurrencyPair.BTC_EUR, CurrencyPair.BTC_USD, CurrencyPair.BTC_KRW, CurrencyPair.NMC_XRP,
      CurrencyPair.NMC_EUR, CurrencyPair.NMC_USD, CurrencyPair.NMC_KRW, CurrencyPair.XVN_XRP, CurrencyPair.USD_XVN, CurrencyPair.EUR_XVN, CurrencyPair.EUR_XRP, CurrencyPair.USD_XRP,
      CurrencyPair.KRW_XRP));

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public KrakenBaseService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");

  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
