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
package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.cryptsy.Cryptsy;
import com.xeiam.xchange.cryptsy.CryptsyAdapters;
import com.xeiam.xchange.cryptsy.CryptsyMarketCoin;
import com.xeiam.xchange.cryptsy.CryptsyTicker;
import com.xeiam.xchange.cryptsy.CryptsyUtils;
import com.xeiam.xchange.cryptsy.RecentTrade;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyDepth;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPair;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Implementation of the market data service for BTCE
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class CryptsyPollingMarketDataService extends BasePollingExchangeService
		implements PollingMarketDataService {

	private final Cryptsy cryptsy;

	private CryptsyTicker ticker = null;
	private CryptsyDepth depth = null;
	private long lastTickerCache = 0;
	private long lastDepthCache = 0;

	/**
	 * @param exchangeSpecification
	 *            The {@link ExchangeSpecification}
	 */
	public CryptsyPollingMarketDataService(
			ExchangeSpecification exchangeSpecification) {
		super(exchangeSpecification);
		cryptsy = RestProxyFactory.createProxy(Cryptsy.class,
				exchangeSpecification.getHost());
	}

	@Override
	public Ticker getTicker(String tradableIdentifier, String currency) {
		if (ticker == null
				|| lastTickerCache + 10000 < System.currentTimeMillis()) {
			ticker = cryptsy.getTicker("marketdatav2");
			lastTickerCache = System.currentTimeMillis();
		}
		return CryptsyAdapters
				.adaptTicker(ticker, tradableIdentifier, currency);
	}

	public List<RecentTrade> getRecentTrades(String aTradeableIdentifier,
			String currency) {
		if (ticker == null
				|| lastTickerCache + 10000 < System.currentTimeMillis()) {
			ticker = cryptsy.getTicker("marketdatav2");
			lastTickerCache = System.currentTimeMillis();
		}

		return ticker.result.getMarkets()
				.get(aTradeableIdentifier.toUpperCase() + "/" + currency)
				.getRecentTrades();
	}

	@Override
	public OrderBook getPartialOrderBook(String tradableIdentifier,
			String currency) {

		throw new NotAvailableFromExchangeException();
	}

	@Override
	public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {

		verify(tradableIdentifier, currency);

		if (depth == null || lastDepthCache + 4000 < System.currentTimeMillis()) {
			depth = cryptsy.getFullDepth("orderdata");
			lastDepthCache = System.currentTimeMillis();
		}
		// Adapt to XChange DTOs
		List<LimitOrder> asks = CryptsyAdapters.adaptOrders(depth.getPairs()
				.get(tradableIdentifier.toUpperCase()), tradableIdentifier,
				currency, "ask");
		List<LimitOrder> bids = CryptsyAdapters.adaptOrders(depth.getPairs()
				.get(tradableIdentifier.toUpperCase()), tradableIdentifier,
				currency, "bid");

		return new OrderBook(new Date(), asks, bids);
	}

	@Override
	public Trades getTrades(String tradableIdentifier, String currency,
			Object... args) {

		throw new NotAvailableFromExchangeException();
	}

	/**
	 * Verify
	 * 
	 * @param tradableIdentifier
	 *            The tradable identifier (e.g. BTC in BTC/USD)
	 * @param currency
	 */
	private void verify(String tradableIdentifier, String currency) {

		Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
		Assert.notNull(currency, "currency cannot be null");
		Assert.isTrue(CryptsyUtils.isValidCurrencyPair(new CurrencyPair(
				tradableIdentifier, currency)), "currencyPair is not valid:"
				+ tradableIdentifier + " " + currency);

	}

	public List<CryptsyMarketCoin> getCurrencyMarkets() {
		if (ticker == null
				|| lastDepthCache + 4000 < System.currentTimeMillis()) {
			ticker = cryptsy.getTicker("marketdatav2");
			lastDepthCache = System.currentTimeMillis();
		}
		List<CryptsyMarketCoin> currencies = new ArrayList<CryptsyMarketCoin>();
		for (String currency : ticker.result.getMarkets().keySet()) {
			currencies.add(ticker.result.getMarkets().get(currency));
		}
		return currencies;
	}

	@Override
	public Set<CurrencyPair> getExchangeSymbols() {
		if (depth == null || lastDepthCache + 4000 < System.currentTimeMillis()) {
			depth = cryptsy.getFullDepth("orderdata");
			lastDepthCache = System.currentTimeMillis();
		}
		Set<CurrencyPair> currencies = new HashSet<CurrencyPair>();
		for (CryptsyPair currency : depth.getPairs().values()) {
			if (!currency.getPrimaryCode().equals("Points")
					&& !currency.getSecondaryCode().equals("Points")) {
				currencies.add(new CurrencyPair(currency.getPrimaryCode()
						.toUpperCase(), currency.getSecondaryCode()
						.toUpperCase()));
			}
		}
		return currencies;
	}

	@Override
	public ExchangeInfo getExchangeInfo() throws IOException,
			NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException {

		return new ExchangeInfo(new ArrayList<CurrencyPair>(
				getExchangeSymbols()));
	}
}
