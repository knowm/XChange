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
package com.xeiam.xchange.itbit.v1.service.polling;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.itbit.v1.ItBitAuthenticated;
import com.xeiam.xchange.itbit.v1.service.ItBitHmacPostBodyDigest;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;

public class ItBitBasePollingService extends BasePollingExchangeService {

	private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
	private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

	protected final String apiKey;
	protected final ItBitAuthenticated itBit;
	protected final ParamsDigest signatureCreator;

	public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(
			new CurrencyPair("XBT", "USD"),
			new CurrencyPair("XBT", "EUR"),
			new CurrencyPair("XBT", "SGD"));

	/**
	 * Constructor
	 * 
	 * @param exchangeSpecification The {@link ExchangeSpecification}
	 */
	public ItBitBasePollingService(ExchangeSpecification exchangeSpecification) {

		super(exchangeSpecification);
		this.itBit = RestProxyFactory.createProxy(
				ItBitAuthenticated.class, 
				(String)exchangeSpecification.getExchangeSpecificParametersItem("authHost"));

		this.apiKey = exchangeSpecification.getApiKey();
		this.signatureCreator = ItBitHmacPostBodyDigest.createInstance(apiKey, exchangeSpecification.getSecretKey());
	}

	protected int nextNonce() {
		return lastNonce.incrementAndGet();
	}

	@Override
	public List<CurrencyPair> getExchangeSymbols() {
		return CURRENCY_PAIRS;
	}
}
