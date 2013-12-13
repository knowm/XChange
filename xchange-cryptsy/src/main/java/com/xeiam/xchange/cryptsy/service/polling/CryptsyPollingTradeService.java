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
package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

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

/** @author Matija Mazi */
public class CryptsyPollingTradeService extends CryptsyBasePollingService implements PollingTradeService {

	public static Map<String, String> marketIds = new HashMap<String, String>();

	/**
	 * Constructor
	 * 
	 * @param exchangeSpecification
	 *          The {@link ExchangeSpecification}
	 */
	public CryptsyPollingTradeService(ExchangeSpecification exchangeSpecification) {

		super(exchangeSpecification);
	}

	@Override
	public OpenOrders getOpenOrders() {
		//
		// BTCEOpenOrdersReturn orders = btce.OrderList(apiKey,
		// signatureCreator, nextNonce(), null, null, null, null,
		// BTCEAuthenticated.SortOrder.DESC, null, null, null, 1);
		// if ("no orders".equals(orders.getError())) {
		// return new OpenOrders(new ArrayList<LimitOrder>());
		// }
		// checkResult(orders);
		// return BTCEAdapters.adaptOrders(orders.getReturnValue());
		return null;
	}

	@Override
	public String placeMarketOrder(MarketOrder marketOrder) {

		throw new UnsupportedOperationException("Market orders not supported by Cryptsy API.");
	}

	@Override
	public String placeLimitOrder(LimitOrder limitOrder) {

		String pair = String.format("%s_%s", limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency()).toUpperCase();
		String marketId = marketIds.get(pair);
		if (marketId == null) {
			throw new ExchangeException("No Cryptsy marketId for " + pair + ", aborting.");
		}
		String type = limitOrder.getType() == Order.OrderType.BID ? "buy" : "sell";
		DecimalFormat format = new DecimalFormat("#.########");
		CryptsyPlaceOrderReturn ret = cryptsy.trade(apiKey, signatureCreator, nextNonce(), "createorder", marketId, type, format.format(limitOrder.getLimitPrice().getAmount().floatValue()), format.format(limitOrder.getTradableAmount().floatValue()));
		if (!ret.getSuccess().equals("1")) {
			throw new ExchangeException("Problem ordering at Cryptsy: " + ret.getError());
		}

		return ret.getOrderId();
		// return ret.getResult().get("orderid").toString();
		// return ret.getResult().get("orderid");
	}

	@Override
	public boolean cancelOrder(String orderId) {

		// BTCECancelOrderReturn ret = btce.CancelOrder(apiKey,
		// signatureCreator, nextNonce(), Long.parseLong(orderId));
		// checkResult(ret);
		// return ret.isSuccess();
		return false;
	}

	public static Map<String, String> getMarketIds() {
		return marketIds;
	}

	public static void setMarketIds(Map<String, String> marketIds) {
		CryptsyPollingTradeService.marketIds = marketIds;
	}

	@Override
	public Trades getTradeHistory(Object... arguments)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
