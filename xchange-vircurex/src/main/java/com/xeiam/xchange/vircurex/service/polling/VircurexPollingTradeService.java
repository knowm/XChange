/**
 * Copyright (C) 2012 - 2013 Matija Mazi
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
package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.vircurex.VircurexUtils;
import com.xeiam.xchange.vircurex.dto.marketdata.VircurexPlaceOrderReturn;

/** @author Matija Mazi */
public class VircurexPollingTradeService implements PollingTradeService {

	ExchangeSpecification exchangeSpecification;
	VircurexAuthenticated vircurex;

	/**
	 * Constructor
	 * 
	 * @param anExchangeSpecification
	 *          The {@link ExchangeSpecification}
	 */
	public VircurexPollingTradeService(ExchangeSpecification anExchangeSpecification) {
		exchangeSpecification = anExchangeSpecification;
		vircurex = RestProxyFactory.createProxy(VircurexAuthenticated.class, exchangeSpecification.getSslUri());

	}

	@Override
	public OpenOrders getOpenOrders() {

		return null;
	}

	@Override
	public String placeMarketOrder(MarketOrder marketOrder) {

		throw new UnsupportedOperationException("Market orders not supported by BTER API.");
	}

	@Override
	public String placeLimitOrder(LimitOrder limitOrder) {

		String type = limitOrder.getType() == Order.OrderType.BID ? "buy" : "sell";
		String timestamp = VircurexUtils.getUtcTimestamp();
		String nonce = (System.currentTimeMillis() / 250L) + "";
		VircurexSha2Digest digest = new VircurexSha2Digest(exchangeSpecification.getApiKey(), exchangeSpecification.getUserName(), timestamp, nonce, "create_order", type.toString(), limitOrder.getTradableAmount().floatValue() + "", limitOrder.getTradableIdentifier().toLowerCase(), limitOrder
				.getLimitPrice().getAmount().floatValue()
				+ "", limitOrder.getTransactionCurrency().toLowerCase());

		VircurexPlaceOrderReturn ret = vircurex.trade(exchangeSpecification.getApiKey(), nonce, digest.toString(), timestamp, type.toString(), limitOrder.getTradableAmount().floatValue() + "", limitOrder.getTradableIdentifier().toLowerCase(), limitOrder.getLimitPrice().getAmount().floatValue() + "",
				limitOrder.getTransactionCurrency().toLowerCase());

		timestamp = VircurexUtils.getUtcTimestamp();
		nonce = (System.currentTimeMillis() / 200L) + "";

		digest = new VircurexSha2Digest(exchangeSpecification.getApiKey(), exchangeSpecification.getUserName(), timestamp, nonce, "release_order", ret.getOrderId());

		ret = vircurex.release(exchangeSpecification.getApiKey(), nonce, digest.toString(), timestamp, ret.getOrderId());
		return ret.getOrderId();
	}

	@Override
	public boolean cancelOrder(String orderId) {

		// BTCECancelOrderReturn ret = btce.CancelOrder(apiKey, signatureCreator,
		// nextNonce(), Long.parseLong(orderId));
		// checkResult(ret);
		// return ret.isSuccess();
		return false;
	}

	@Override
	public Trades getTradeHistory(Object... arguments)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
