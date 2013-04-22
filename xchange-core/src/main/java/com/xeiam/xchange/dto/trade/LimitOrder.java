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
package com.xeiam.xchange.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.joda.money.BigMoney;

import com.xeiam.xchange.dto.Order;

/**
 * <p>
 * DTO representing a limit order
 * </p>
 * <p>
 * A limit order lets you set a minimum or maximum price before your trade will be treated by the exchange as a {@link MarketOrder}. There is no
 * guarantee that your conditions will be met on the exchange, so your order may not be executed. However, until you become very experienced, almost
 * all orders should be limit orders to protect yourself.
 * </p>
 */
public final class LimitOrder extends Order implements Comparable<LimitOrder> {

	/**
	 * The limit price
	 */
	private final BigMoney limitPrice;

	/**
	 * @param type
	 *            Either BID (buying) or ASK (selling)
	 * @param tradableAmount
	 *            The amount to trade
	 * @param tradableIdentifier
	 *            The identifier (e.g. BTC in BTC/USD)
	 * @param transactionCurrency
	 *            The transaction currency (e.g. USD in BTC/USD)
	 * @param id
	 *            An id (usually provided by the exchange)
	 * @param limitPrice
	 *            In a BID this is the highest acceptable price, in an ASK this is the lowest acceptable price
	 * @param timestamp
	 *            a Date object representing the order's timestamp
	 */
	public LimitOrder(OrderType type, BigDecimal tradableAmount, String tradableIdentifier, String transactionCurrency, String id, Date timestamp,
			BigMoney limitPrice) {

		super(type, tradableAmount, tradableIdentifier, transactionCurrency, id, timestamp);
		this.limitPrice = limitPrice;
	}

	/**
	 * @param type
	 *            Either BID (buying) or ASK (selling)
	 * @param tradableAmount
	 *            The amount to trade
	 * @param tradableIdentifier
	 *            The identifier (e.g. BTC in BTC/USD)
	 * @param transactionCurrency
	 *            The transaction currency (e.g. USD in BTC/USD)
	 * @param id
	 *            An id (usually provided by the exchange)
	 * @param limitPrice
	 *            In a BID this is the highest acceptable price, in an ASK this is the lowest acceptable price
	 */
	public LimitOrder(OrderType type, BigDecimal tradableAmount, String tradableIdentifier, String transactionCurrency, String id, BigMoney limitPrice) {

		super(type, tradableAmount, tradableIdentifier, transactionCurrency, id, null);
		this.limitPrice = limitPrice;
	}

	/**
	 * @param type
	 *            Either BID (buying) or ASK (selling)
	 * @param tradableAmount
	 *            The amount to trade
	 * @param tradableIdentifier
	 *            The identifier (e.g. BTC in BTC/USD)
	 * @param transactionCurrency
	 *            The transaction currency (e.g. USD in BTC/USD)
	 * @param limitPrice
	 *            In a BID this is the highest acceptable price, in an ASK this is the lowest acceptable price
	 * @param timestamp
	 *            a Date object representing the order's timestamp
	 */
	public LimitOrder(OrderType type, BigDecimal tradableAmount, String tradableIdentifier, String transactionCurrency, BigMoney limitPrice,
			Date timestamp) {

		super(type, tradableAmount, tradableIdentifier, transactionCurrency, "", timestamp);
		this.limitPrice = limitPrice;
	}

	/**
	 * @param type
	 *            Either BID (buying) or ASK (selling)
	 * @param tradableAmount
	 *            The amount to trade
	 * @param tradableIdentifier
	 *            The identifier (e.g. BTC in BTC/USD)
	 * @param transactionCurrency
	 *            The transaction currency (e.g. USD in BTC/USD)
	 * @param limitPrice
	 *            In a BID this is the highest acceptable price, in an ASK this is the lowest acceptable price
	 */
	public LimitOrder(OrderType type, BigDecimal tradableAmount, String tradableIdentifier, String transactionCurrency, BigMoney limitPrice) {

		super(type, tradableAmount, tradableIdentifier, transactionCurrency, "", null);
		this.limitPrice = limitPrice;
	}

	/**
	 * @return The limit price
	 */
	public BigMoney getLimitPrice() {

		return limitPrice;
	}

	@Override
	public String toString() {

		return "LimitOrder [limitPrice=" + limitPrice + ", " + super.toString() + "]";
	}

	@Override
	public int compareTo(LimitOrder limitOrder) {

		return this.getLimitPrice().getAmount().compareTo(limitOrder.getLimitPrice().getAmount()) * (getType() == OrderType.BID ? -1 : 1);

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj == null || obj.getClass() != this.getClass())
			return false;

		LimitOrder rhs = (LimitOrder) obj;
		return super.equals(obj) && Objects.equals(getLimitPrice(), rhs.getLimitPrice());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getLimitPrice());
	}
}
