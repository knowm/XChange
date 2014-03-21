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
package com.xeiam.xchange.vircurex.service;

import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * @author timmolter
 */
public class VircurexBaseService extends BaseExchangeService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = new ArrayList<CurrencyPair>();

  static {

    CURRENCY_PAIRS.add(CurrencyPair.LTC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.TRC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.PPC_BTC);
    CURRENCY_PAIRS.add(new CurrencyPair("DGC", Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair(Currencies.NVC, Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair(Currencies.NMC, Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair(Currencies.TRC, Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair(Currencies.DVC, Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair(Currencies.IXC, Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair("FRC", Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair(Currencies.FTC, Currencies.LTC));
    CURRENCY_PAIRS.add(CurrencyPair.NMC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.BTC_USD);
    CURRENCY_PAIRS.add(new CurrencyPair("ANC", Currencies.BTC));
    CURRENCY_PAIRS.add(CurrencyPair.XDC_BTC);
  }

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public VircurexBaseService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
