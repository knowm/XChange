/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.btce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.time.DateTime;

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;
import com.xeiam.xchange.utils.MoneyUtils;
import com.xeiam.xchange.btce.dto.marketdata.BTCETicker;
import com.xeiam.xchange.btce.dto.marketdata.BTCETrade;

/**
 * Various adapters for converting from BTCE DTOs to XChange DTOs
 */
public class BTCEAdapters {

	/**
	 * Adapts a BTCEOrder to a LimitOrder
	 * 
	 * @param amount
	 * @param price
	 * @param currency
	 * @param orderTypeString
	 * @param id
	 * @return
	 */
	public static LimitOrder adaptOrder(double amount, double price,
			String currency, String orderTypeString, String id) {

		// place a limit order
		OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID
				: OrderType.ASK;
		BigDecimal tradeableAmount = new BigDecimal(amount);
		String tradableIdentifier = Currencies.BTC;
		String transactionCurrency = currency;
		BigMoney limitPrice = MoneyUtils.parseFiat(currency + " " + price);

		LimitOrder limitOrder = new LimitOrder(orderType, tradeableAmount,
				tradableIdentifier, transactionCurrency, limitPrice);

		return limitOrder;

	}

	/**
	 * Adapts a List of BTCEOrders to a List of LimitOrders
	 * 
	 * @param BTCEOrders
	 * @param currency
	 * @param orderType
	 * @param id
	 * @return
	 */
	public static List<LimitOrder> adaptOrders(List<double[]> BTCEOrders,
			String currency, String orderType, String id) {

		List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

		for (double[] BTCEOrder : BTCEOrders) {
			// Bid orderbook is reversed order. Insert at index 0 instead of
			// appending
			if (orderType.equalsIgnoreCase("bid")) {
				limitOrders.add(
						0,
						adaptOrder(BTCEOrder[1], BTCEOrder[0], currency,
								orderType, id));
			} else {
				limitOrders.add(adaptOrder(BTCEOrder[1], BTCEOrder[0],
						currency, orderType, id));
			}
		}

		return limitOrders;
	}

	/**
	 * Adapts a BTCETrade to a Trade Object
	 * 
	 * @param BTCETrade
	 *            A BTCE trade
	 * @return The XChange Trade
	 */
	public static Trade adaptTrade(BTCETrade BTCETrade) {

		OrderType orderType = BTCETrade.equals("bid") ? OrderType.BID
				: OrderType.ASK;
		BigDecimal amount = new BigDecimal(BTCETrade.getAmount());
		BigMoney price = BTCEUtils.getPrice("USD", BTCETrade.getPrice());
		String tradableIdentifier = BTCETrade.getItem();
		DateTime dateTime = DateUtils
				.fromMillisUtc(BTCETrade.getDate() * 1000L);

		return new Trade(orderType, amount, tradableIdentifier, "USD", price,
				dateTime);
	}

	/**
	 * Adapts a BTCETrade[] to a Trades Object
	 * 
	 * @param BTCETrades
	 *            The BTCE trade data
	 * @return The trades
	 */
	public static Trades adaptTrades(BTCETrade[] BTCETrades) {

		List<Trade> tradesList = new ArrayList<Trade>();
		for (BTCETrade BTCETrade : BTCETrades) {
			// Date is reversed order. Insert at index 0 instead of appending
			tradesList.add(0, adaptTrade(BTCETrade));
		}
		return new Trades(tradesList);
	}

	public static String getPriceString(BigMoney price) {

		return price.getAmount().stripTrailingZeros().toPlainString();
	}

	/**
	 * Adapts a BTCETicker to a Ticker Object
	 * 
	 * @param BTCETicker
	 * @return
	 */
	public static Ticker adaptTicker(BTCETicker BTCETicker) {

		BigMoney last = MoneyUtils.parseFiat("USD" + " "
				+ BTCETicker.getTicker().getLast());
		BigMoney bid = MoneyUtils.parseFiat("USD" + " "
				+ BTCETicker.getTicker().getSell());
		BigMoney ask = MoneyUtils.parseFiat("USD" + " "
				+ BTCETicker.getTicker().getBuy());
		BigMoney high = MoneyUtils.parseFiat("USD" + " "
				+ BTCETicker.getTicker().getHigh());
		BigMoney low = MoneyUtils.parseFiat("USD" + " "
				+ BTCETicker.getTicker().getLow());
		BigDecimal volume = new BigDecimal(BTCETicker.getTicker().getVol());

		// return new Ticker("CAD", last, bid, ask, high, low, volume);
		return TickerBuilder.newInstance().withTradableIdentifier(Currencies.BTC)
				.withLast(last).withBid(bid).withAsk(ask).withHigh(high)
				.withLow(low).withVolume(volume).build();
	}

}
