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
package com.xeiam.xchange.bitfinex.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexLend {
	
	private final BigDecimal rate;
	private final BigDecimal amountLent;
	private final long timestamp;
	
	/**
	 * Constructor
	 * 
	 * @param rate
	 * @param amountLent
	 * @param timestamp
	 */
	public BitfinexLend(@JsonProperty("rate") BigDecimal rate, @JsonProperty("amount_lent") BigDecimal amountLent, @JsonProperty("timestamp") long timestamp) {
		
		this.rate = rate;
		this.amountLent = amountLent;
		this.timestamp = timestamp;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public BigDecimal getAmountLent() {
		return amountLent;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "BitfinexLend [rate=" + rate + ", amountLent=" + amountLent
				+ ", timestamp=" + timestamp + "]";
	}
	
}
