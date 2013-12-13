/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
import com.xeiam.xchange.cryptsy.service.polling.CryptsyPollingAccountService;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyPollingMarketDataService;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyPollingTradeService;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the BTCE exchange API</li>
 * </ul>
 */
public class CryptsyExchange extends BaseExchange implements Exchange {

	/**
	 * Default constructor for ExchangeFactory
	 */
	public CryptsyExchange() {

	}

	/**
	 * @return A default configuration for this exchange
	 */
	public static Exchange newInstance() {

		Exchange exchange = new CryptsyExchange();
		exchange.applySpecification(exchange.getDefaultExchangeSpecification());
		return exchange;
	}

	@Override
	public void applySpecification(ExchangeSpecification exchangeSpecification) {

		super.applySpecification(exchangeSpecification);

		this.pollingMarketDataService = new CryptsyPollingMarketDataService(exchangeSpecification);
		this.pollingAccountService = new CryptsyPollingAccountService(exchangeSpecification);
		this.pollingTradeService = new CryptsyPollingTradeService(exchangeSpecification);
		CryptsyUtils.CURRENCY_PAIRS.clear();

		for (CryptsyMarketCoin coin : new CryptsyPollingMarketDataService(exchangeSpecification).getCurrencyMarkets()) {
			CryptsyPollingTradeService.getMarketIds().put(coin.primaryCode + "_" + coin.secondaryCode, coin.marketId);
			CryptsyUtils.CURRENCY_PAIRS.add(new CurrencyPair(coin.primaryCode, coin.secondaryCode));
		}
	}

	@Override
	public ExchangeSpecification getDefaultExchangeSpecification() {

		ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
		exchangeSpecification.setSslUri("https://www.cryptsy.com");
		exchangeSpecification.setHost("http://pubapi.cryptsy.com");
		exchangeSpecification.setPort(443);
		exchangeSpecification.setExchangeName("Cryptsy");
		exchangeSpecification.setExchangeDescription("Cryptsy");

		return exchangeSpecification;
	}

	@Override
	public boolean isSupportedCurrencyPair(CurrencyPair aPair) {
		return CryptsyUtils.isValidCurrencyPair(aPair);
	}
}
