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
package com.xeiam.xchange.cryptotrade.service;

import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;

public class CryptoTradeBaseService extends BaseExchangeService {

	public static final List<CurrencyPair> CURRENCY_PAIRS = new ArrayList<CurrencyPair>();

	static {

		CURRENCY_PAIRS.add(CurrencyPair.BTC_USD);
		CURRENCY_PAIRS.add(CurrencyPair.BTC_EUR);
		CURRENCY_PAIRS.add(CurrencyPair.LTC_USD);
		CURRENCY_PAIRS.add(CurrencyPair.LTC_EUR);
		CURRENCY_PAIRS.add(CurrencyPair.LTC_BTC);
		CURRENCY_PAIRS.add(CurrencyPair.NMC_BTC);
		CURRENCY_PAIRS.add(CurrencyPair.NMC_USD);
		CURRENCY_PAIRS.add(CurrencyPair.XPM_BTC);
		CURRENCY_PAIRS.add(CurrencyPair.XPM_USD);
		CURRENCY_PAIRS.add(CurrencyPair.XPM_PPC);
		CURRENCY_PAIRS.add(CurrencyPair.PPC_BTC);
		CURRENCY_PAIRS.add(CurrencyPair.PPC_USD);
		CURRENCY_PAIRS.add(CurrencyPair.FTC_USD);
		CURRENCY_PAIRS.add(CurrencyPair.FTC_BTC);
		CURRENCY_PAIRS.add(CurrencyPair.TRC_BTC);
		CURRENCY_PAIRS.add(CurrencyPair.CNC_BTC);
		CURRENCY_PAIRS.add(CurrencyPair.WDC_BTC);
		CURRENCY_PAIRS.add(CurrencyPair.DVC_BTC);
	}

	/**
	 * Constructor
	 * 
	 * @param exchangeSpecification
	 *            The {@link ExchangeSpecification}
	 */
	public CryptoTradeBaseService(ExchangeSpecification exchangeSpecification) {

		super(exchangeSpecification);

	}

	@Override
	public List<CurrencyPair> getExchangeSymbols() {

		return CURRENCY_PAIRS;
	}
}
