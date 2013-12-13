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
package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.cryptotrade.CryptoTrade;
import com.xeiam.xchange.cryptotrade.CryptoTradeAdapters;
import com.xeiam.xchange.cryptotrade.CryptoTradeUtils;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeDepth;
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
public class CryptoTradePollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

	private final CryptoTrade cryptoTrade;

	/**
	 * @param exchangeSpecification
	 *          The {@link ExchangeSpecification}
	 */
	public CryptoTradePollingMarketDataService(ExchangeSpecification exchangeSpecification) {
		super(exchangeSpecification);
		cryptoTrade = RestProxyFactory.createProxy(CryptoTrade.class, exchangeSpecification.getSslUri());
	}

	@Override
	public Ticker getTicker(String tradableIdentifier, String currency) {

		throw new NotAvailableFromExchangeException();
	}

	@Override
	public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) {

		throw new NotAvailableFromExchangeException();
	}

	@Override
	public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {

		verify(tradableIdentifier, currency);

		CryptoTradeDepth btceDepth = cryptoTrade.getFullDepth(tradableIdentifier.toLowerCase(), currency.toLowerCase());

		// Adapt to XChange DTOs
		List<LimitOrder> asks = CryptoTradeAdapters.adaptOrders(btceDepth.getAsks(), tradableIdentifier, currency, "ask", "");
		List<LimitOrder> bids = CryptoTradeAdapters.adaptOrders(btceDepth.getBids(), tradableIdentifier, currency, "bid", "");

		return new OrderBook(new Date(), asks, bids);
	}

	@Override
	public Trades getTrades(String tradableIdentifier, String currency, Object... args) {

		throw new NotAvailableFromExchangeException();
	}

	/**
	 * Verify
	 * 
	 * @param tradableIdentifier
	 *          The tradable identifier (e.g. BTC in BTC/USD)
	 * @param currency
	 */
	private void verify(String tradableIdentifier, String currency) {

		Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
		Assert.notNull(currency, "currency cannot be null");
		Assert.isTrue(CryptoTradeUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);

	}

	@Override
	public Set<CurrencyPair> getExchangeSymbols() {

		return CryptoTradeUtils.CURRENCY_PAIRS;
	}

	@Override
	public ExchangeInfo getExchangeInfo() throws IOException,
			NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException {
		// TODO Auto-generated method stub
		return null;
	}

}
