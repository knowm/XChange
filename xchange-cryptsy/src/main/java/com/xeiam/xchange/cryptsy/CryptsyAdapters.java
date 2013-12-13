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

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.joda.money.IllegalCurrencyException;

import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyOrder;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPair;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyAccountInfo;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * Various adapters for converting from BTCE DTOs to XChange DTOs
 */
public final class CryptsyAdapters {

	/**
	 * private Constructor
	 */
	private CryptsyAdapters() {

	}

	/**
	 * Adapts a BTEROrder to a LimitOrder
	 * 
	 * @param amount
	 * @param price
	 * @param currency
	 * @param orderTypeString
	 * @param id
	 * @return
	 */
	public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, String tradableIdentifier, String currency, String orderTypeString) {

		// place a limit order
		OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
		BigMoney limitPrice;
		limitPrice = MoneyUtils.parse(currency + " " + price);

		return new LimitOrder(orderType, amount, tradableIdentifier, currency, limitPrice);

	}

	/**
	 * Adapts a List of BTCEOrders to a List of LimitOrders
	 * 
	 * @param bTEROrders
	 * @param currency
	 * @param orderType
	 * @param id
	 * @return
	 */
	public static List<LimitOrder> adaptOrders(CryptsyPair pair, String tradableIdentifier, String currency, String orderType) {

		List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
		if (pair == null) {
			System.out.println("Couldn't get the marketdata for " + tradableIdentifier + " on Cryptsy.");
			return limitOrders;
		}
		if (orderType.equalsIgnoreCase("bid") && pair.getBuyOrders() != null) {
			for (CryptsyOrder order : pair.getBuyOrders()) {
				if (order != null && Float.parseFloat(order.getQuantity()) > 0.00001) {
					limitOrders.add(0, adaptOrder(new BigDecimal(order.getQuantity()), new BigDecimal(order.getPrice()), tradableIdentifier, currency, orderType));
				}
			}
		} else if(pair.getSellOrders() != null) {
			for (CryptsyOrder order : pair.getSellOrders()) {
				if (order != null && Float.parseFloat(order.getQuantity()) > 0.00001) {
					limitOrders.add(adaptOrder(new BigDecimal(order.getQuantity()), new BigDecimal(order.getPrice()), tradableIdentifier, currency, orderType));
				}
			}
		}

		return limitOrders;
	}

	/**
	 * Adapts a BTCETicker to a Ticker Object
	 * 
	 * @param bTCETicker
	 * @return
	 */
	public static Ticker adaptTicker(CryptsyTicker cryptsyTicker, String tradableIdentifier, String currency) {
		CryptsyMarketCoin coin = cryptsyTicker.result.markets.get(tradableIdentifier.toUpperCase() + "/" + currency.toUpperCase());
		if (coin == null) {
			return null;
		}
		RecentTrade trade = coin.recentTrades.get(0);
		try {
			return TickerBuilder.newInstance().withTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(trade.getTime())).withLast(MoneyUtils.parse(tradableIdentifier.toUpperCase() + " " + trade.price)).withVolume(trade.quantity).withTradableIdentifier(tradableIdentifier.toUpperCase()).build();
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static AccountInfo adaptAccountInfo(String username, CryptsyAccountInfo cryptsyAccountInfo) {
		List<Wallet> wallets = new ArrayList<Wallet>();
		List<Wallet> unavailableWallets = new ArrayList<Wallet>();
		Map<String, BigDecimal> availableFunds = cryptsyAccountInfo.getAvailableFunds();
		for (String lcCurrency : availableFunds.keySet()) {
			String currency = lcCurrency.toUpperCase();
			try {
				CurrencyUnit.of(currency);
			} catch (IllegalCurrencyException e) {
				// log.in("Ignoring unknown currency {}", currency);
				continue;
			}
			wallets.add(Wallet.createInstance(currency, availableFunds.get(lcCurrency)));
		}

		Map<String, BigDecimal> unavailableFunds = cryptsyAccountInfo.getLockedFunds();
		for (String lcCurrency : unavailableFunds.keySet()) {
			String currency = lcCurrency.toUpperCase();
			try {
				CurrencyUnit.of(currency);
			} catch (IllegalCurrencyException e) {
				// log.in("Ignoring unknown currency {}", currency);
				continue;
			}
			unavailableWallets.add(Wallet.createInstance(currency, unavailableFunds.get(lcCurrency)));
		}

		return new AccountInfo(username, wallets);

	}

}
