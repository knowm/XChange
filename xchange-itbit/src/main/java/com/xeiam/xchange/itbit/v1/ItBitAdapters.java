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
package com.xeiam.xchange.itbit.v1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.itbit.v1.dto.account.ItBitAccountBalance;
import com.xeiam.xchange.itbit.v1.dto.account.ItBitAccountInfoReturn;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitTrade;
import com.xeiam.xchange.itbit.v1.dto.trade.ItBitOrder;
import com.xeiam.xchange.utils.DateUtils;


public final class ItBitAdapters {
	private static final OpenOrders noOpenOrders = new OpenOrders(Collections.<LimitOrder>emptyList());

	/**
	 * private Constructor
	 */
	private ItBitAdapters() {

	}

	public static Trades adaptTrades(ItBitTrade[] trades, CurrencyPair currencyPair) {
		List<Trade> tradesList = new ArrayList<Trade>(trades.length);

		for(int i = 0; i < trades.length; i++) {
			ItBitTrade trade = trades[i];
			tradesList.add(adaptTrade(trade, currencyPair));
		}
		return new Trades(tradesList, TradeSortType.SortByID);	
	}

	public static Trade adaptTrade(ItBitTrade trade, CurrencyPair currencyPair) {
		Date date = DateUtils.fromMillisUtc(trade.getDate() * 1000L);
		final String tradeId = String.valueOf(trade.getTid());

		return new Trade(null, trade.getAmount(), currencyPair, trade.getPrice(), date, tradeId);
	}


	public static List<LimitOrder> adaptOrders(List<BigDecimal[]> orders, CurrencyPair currencyPair, OrderType orderType) {
		List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.size());

		for(int i = 0; i < orders.size(); i++) {
			BigDecimal[] level = orders.get(i);

			limitOrders.add(adaptOrder(level[1], level[0], currencyPair, null, orderType));
		}

		return limitOrders;

	}

	private static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, String orderId, OrderType orderType) {		
		return new LimitOrder(orderType, amount, currencyPair, orderId, null, price);
	}

	public static AccountInfo adaptAccountInfo(ItBitAccountInfoReturn[] info) {
		List<Wallet> wallets = new ArrayList<Wallet>();
		String userId = "";

		for(int i = 0; i < info.length; i++) {
			ItBitAccountInfoReturn itBitAccountInfoReturn = info[i];
			ItBitAccountBalance[] balances = itBitAccountInfoReturn.getBalances();

			userId = itBitAccountInfoReturn.getUserId();

			for(int j = 0; j < balances.length; j++) {
				ItBitAccountBalance itBitAccountBalance = balances[j];

				Wallet wallet = new Wallet(itBitAccountBalance.getCurrency(), itBitAccountBalance.getAvailableBalance(), itBitAccountInfoReturn.getName());
				wallets.add(wallet);		
			}
		}

		return new AccountInfo(userId, wallets);
	}

	public static OpenOrders adaptPrivateOrders(ItBitOrder[] orders) {
		if(orders.length <= 0) {
			return noOpenOrders;
		}

		List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.length);

		for(int i = 0; i < orders.length; i++) {
			ItBitOrder itBitOrder = orders[i];
			String instrument = itBitOrder.getInstrument();

			CurrencyPair currencyPair = new CurrencyPair(instrument.substring(0,3), instrument.substring(3,6));
			OrderType orderType = itBitOrder.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;

			limitOrders.add(adaptOrder(itBitOrder.getAmount(), itBitOrder.getPrice(), currencyPair, itBitOrder.getId(), orderType));
		}

		return new OpenOrders(limitOrders);
	}

	public static Trades adaptTradeHistory(ItBitOrder[] orders) {
		List<Trade> trades = new ArrayList<Trade>(orders.length);

		for(int i = 0; i < orders.length; i++) {
			ItBitOrder itBitOrder = orders[i]; 
			String instrument = itBitOrder.getInstrument();

			OrderType orderType = itBitOrder.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
			CurrencyPair currencyPair = new CurrencyPair(instrument.substring(0,3), instrument.substring(3,6));

			trades.add(new Trade(orderType, itBitOrder.getAmount(), currencyPair, itBitOrder.getPrice(), itBitOrder.getCreatedTime(), itBitOrder.getId()));
		}

		return new Trades(trades, TradeSortType.SortByTimestamp);
	}
}
