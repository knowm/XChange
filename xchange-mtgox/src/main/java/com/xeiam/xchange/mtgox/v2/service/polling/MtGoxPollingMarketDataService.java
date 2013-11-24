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
package com.xeiam.xchange.mtgox.v2.service.polling;

import java.util.List;
import java.util.Set;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.mtgox.v2.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v2.MtGoxV2;
import com.xeiam.xchange.mtgox.v2.dto.MtGoxException;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxDepthWrapper;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTickerWrapper;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTradesWrapper;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Implementation of the market data service for Mt Gox V2
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class MtGoxPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

	private final MtGoxV2 mtGoxV2;

	/**
	 * Constructor
	 * 
	 * @param exchangeSpecification
	 *          The {@link ExchangeSpecification}
	 */
	public MtGoxPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

		super(exchangeSpecification);
		this.mtGoxV2 = RestProxyFactory.createProxy(MtGoxV2.class, exchangeSpecification.getSslUri());
	}

	@Override
	public Ticker getTicker(String tradableIdentifier, String currency) {

		verify(tradableIdentifier, currency);

		try {
			// Request data
			MtGoxTickerWrapper mtGoxTickerWrapper = mtGoxV2.getTicker(tradableIdentifier, currency);

			if (mtGoxTickerWrapper.getResult().equals("success")) {
				// Adapt to XChange DTOs
				return MtGoxAdapters.adaptTicker(mtGoxTickerWrapper.getMtGoxTicker());
			} else if (mtGoxTickerWrapper.getResult().equals("error")) {
				throw new ExchangeException("Error calling getTicker(): " + mtGoxTickerWrapper.getError());
			} else {
				throw new ExchangeException("Error calling getTicker(): Unexpected result!");
			}
		} catch (MtGoxException e) {
			throw new ExchangeException("Error calling getTicker(): " + e.getError());
		}
	}

	@Override
	public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) {

		verify(tradableIdentifier, currency);

		try {
			// Request data
			MtGoxDepthWrapper mtGoxDepthWrapper = mtGoxV2.getDepth(tradableIdentifier, currency);
			if (mtGoxDepthWrapper.getResult().equals("success")) {
				// Adapt to XChange DTOs
				List<LimitOrder> asks = MtGoxAdapters.adaptOrders(mtGoxDepthWrapper.getMtGoxDepth().getAsks(), currency, "ask", "");
				List<LimitOrder> bids = MtGoxAdapters.adaptOrders(mtGoxDepthWrapper.getMtGoxDepth().getBids(), currency, "bid", "");
				return new OrderBook(asks, bids);
			} else if (mtGoxDepthWrapper.getResult().equals("error")) {
				throw new ExchangeException("Error calling getPartialOrderBook(): " + mtGoxDepthWrapper.getError());
			} else {
				throw new ExchangeException("Error calling getPartialOrderBook(): Unexpected result!");
			}
		} catch (MtGoxException e) {
			throw new ExchangeException("Error calling getPartialOrderBook(): " + e.getError());
		}
	}

	@Override
	public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {

		verify(tradableIdentifier, currency);

		try {
			MtGoxDepthWrapper mtGoxDepthWrapper = mtGoxV2.getFullDepth(tradableIdentifier, currency);
			if (mtGoxDepthWrapper.getResult().equals("success")) {
				// Adapt to XChange DTOs
				List<LimitOrder> asks = MtGoxAdapters.adaptOrders(mtGoxDepthWrapper.getMtGoxDepth().getAsks(), currency, "ask", "");
				List<LimitOrder> bids = MtGoxAdapters.adaptOrders(mtGoxDepthWrapper.getMtGoxDepth().getBids(), currency, "bid", "");
				return new OrderBook(asks, bids);
			} else if (mtGoxDepthWrapper.getResult().equals("error")) {
				throw new ExchangeException("Error calling getFullOrderBook(): " + mtGoxDepthWrapper.getError());
			} else {
				throw new ExchangeException("Error calling getFullOrderBook(): Unexpected result!");
			}
		} catch (MtGoxException e) {
			throw new ExchangeException("Error calling getFullOrderBook(): " + e.getError());
		}
	}

	@Override
	public Trades getTrades(String tradableIdentifier, String currency, Object... args) {

		verify(tradableIdentifier, currency);

		try {
			MtGoxTradesWrapper mtGoxTradeWrapper = null;

			if (args.length > 0) {
				Long sinceTimeStamp = (Long) args[0];
				// Request data
				mtGoxTradeWrapper = mtGoxV2.getTrades(tradableIdentifier, currency, sinceTimeStamp);
			} else {
				// Request data
				mtGoxTradeWrapper = mtGoxV2.getTrades(tradableIdentifier, currency);
			}

			if (mtGoxTradeWrapper.getResult().equals("success")) {
				return MtGoxAdapters.adaptTrades(mtGoxTradeWrapper.getMtGoxTrades());
			} else if (mtGoxTradeWrapper.getResult().equals("error")) {
				throw new ExchangeException("Error calling getTrades(): " + mtGoxTradeWrapper.getError());
			} else {
				throw new ExchangeException("Error calling getTrades(): Unexpected result!");
			}
		} catch (MtGoxException e) {
			throw new ExchangeException("Error calling getTrades(): " + e.getError());
		}
	}

	/**
	 * Verify
	 * 
	 * @param tradableIdentifier
	 *          The tradeable identifier (e.g. BTC in BTC/USD)
	 * @param currency
	 *          The transaction currency (e.g. USD in BTC/USD)
	 */
	private void verify(String tradableIdentifier, String currency) {

		Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
		Assert.notNull(currency, "currency cannot be null");
		Assert.isTrue(MtGoxUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);

	}

	@Override
	public Set<CurrencyPair> getExchangeSymbols() {

		return MtGoxUtils.CURRENCY_PAIRS;
	}
}
