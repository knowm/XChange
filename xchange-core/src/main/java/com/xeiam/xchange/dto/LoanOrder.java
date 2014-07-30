/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.dto.Order.OrderType;

/**
 * Data object representing an order for a loan
 */
public class LoanOrder {
	/**
	 * Order type i.e. bid or ask
	 */
	private final OrderType type;
	
	/**
	 * The loan currency
	 */
	private final String currency;
	
	/**
	 * Amount to be ordered / amount that was ordered
	 */
	private final BigDecimal tradableAmount;
	
	/**
	 * Duration of loan in days
	 */
	private final int dayPeriod;
	
	/**
	 * An identifier that uniquely identifies the order
	 */
	private final String id;
	
	/**
	 * The timestamp on the order
	 */
	private final Date timestamp;
	
	public LoanOrder(OrderType type, String currency, BigDecimal tradableAmount, int dayPeriod, String id, Date timestamp) {
		
		this.type = type;
		this.currency = currency;
		this.tradableAmount = tradableAmount;
		this.dayPeriod = dayPeriod;
		this.id = id;
		this.timestamp = timestamp;
	}

	public OrderType getType() {
		return type;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getTradableAmount() {
		return tradableAmount;
	}

	public int getDayPeriod() {
		return dayPeriod;
	}

	public String getId() {
		return id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "LoanOrder [type=" + type + ", currency=" + currency
				+ ", tradableAmount=" + tradableAmount + ", dayPeriod="
				+ dayPeriod + ", id=" + id + ", timestamp=" + timestamp + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + dayPeriod;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result
				+ ((tradableAmount == null) ? 0 : tradableAmount.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanOrder other = (LoanOrder) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (dayPeriod != other.dayPeriod)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (tradableAmount == null) {
			if (other.tradableAmount != null)
				return false;
		} else if (!tradableAmount.equals(other.tradableAmount))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
}
