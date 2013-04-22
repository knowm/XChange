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
package com.xeiam.xchange.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Data object representing an order
 */

public class Order {

	public enum OrderType {

		/**
		 * Buying order (you're making an offer)
		 */
		BID,
		/**
		 * Selling order (you're asking for offers)
		 */
		ASK
	}

	/**
	 * Order type i.e. bid or ask
	 */
	private final OrderType type;

	/**
	 * Amount to be ordered / amount that was ordered
	 */
	private final BigDecimal tradableAmount;

	/**
	 * An identifier that uniquely identifies the tradeable
	 */
	private final String tradableIdentifier;

	/**
	 * The currency used to settle the market order transaction
	 */
	private final String transactionCurrency;

	/**
	 * An identifier that uniquely identifies the order
	 */
	private final String id;

	/**
	 * The timestamp on the order
	 */
	private final Date timestamp;

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
	 */
	public Order(OrderType type, BigDecimal tradableAmount, String tradableIdentifier, String transactionCurrency, String id, Date timestamp) {

		this.type = type;
		this.tradableAmount = tradableAmount;
		this.tradableIdentifier = tradableIdentifier;
		this.transactionCurrency = transactionCurrency;
		this.id = id;
		this.timestamp = timestamp;
	}

	/**
	 * @return The type (BID or ASK)
	 */
	public OrderType getType() {

		return type;
	}

	/**
	 * @return The amount to trade
	 */
	public BigDecimal getTradableAmount() {

		return tradableAmount;
	}

	/**
	 * @return The tradeable identifier (e.g. BTC in BTC/USD)
	 */
	public String getTradableIdentifier() {

		return tradableIdentifier;
	}

	/**
	 * @return The transaction currency (e.g. USD in BTC/USD)
	 */
	public String getTransactionCurrency() {

		return transactionCurrency;
	}

	/**
	 * @return A unique identifier (normally provided by the exchange)
	 */
	public String getId() {

		return id;
	}

	public Date getTimestamp() {

		return timestamp;
	}

	@Override
	public String toString() {

		return "Order [type=" + type + ", tradableAmount=" + tradableAmount + ", tradableIdentifier=" + tradableIdentifier + ", transactionCurrency="
				+ transactionCurrency + ", id=" + id + ", timestamp=" + timestamp + "]";
	}

	
	/*
	 * @return Equality. Intentionally does not compare timestamp.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj == null || obj.getClass() != this.getClass())
			return false;

		Order rhs = (Order) obj;
		return Objects.equals(getType(), rhs.getType()) && Objects.equals(getTradableAmount(), rhs.getTradableAmount())
				&& Objects.equals(getTransactionCurrency(), rhs.getTransactionCurrency()) && Objects.equals(getId(), rhs.getId());
	}

	/*
	 * @return Hashcode. Intentionally does not compare timestamp;
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getType(), getTradableAmount(), getTransactionCurrency(), getId());
	}
}
