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
package com.xeiam.xchange.bitcurex.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcurexTicker {

	private final BigDecimal avg;
	private final BigDecimal buy;
	private final BigDecimal high;
	private final BigDecimal last;
	private final BigDecimal low;
	private final BigDecimal sell;
	private final BigDecimal time;
	private final BigDecimal vol;
	private final BigDecimal vwap;

	/**
	 * @param high
	 * @param low
	 * @param vol
	 * @param last
	 * @param avg
	 * @param buy
	 * @param sell
	 * @param vwap
	 * @param time
	 */
	public BitcurexTicker(@JsonProperty("high") BigDecimal high,
			@JsonProperty("low") BigDecimal low,
			@JsonProperty("vol") BigDecimal vol,
			@JsonProperty("last") BigDecimal last,
			@JsonProperty("avg") BigDecimal avg,
			@JsonProperty("buy") BigDecimal buy,
			@JsonProperty("sell") BigDecimal sell,
			@JsonProperty("vwap") BigDecimal vwap,
			@JsonProperty("time") BigDecimal time) {

		this.high = high;
		this.low = low;
		this.vol = vol;
		this.last = last;
		this.time = time;
		this.sell = sell;
		this.buy = buy;
		this.vwap = vwap;
		this.avg = avg;
	}

	public BigDecimal getAvg() {
		return this.avg;
	}

	public BigDecimal getBuy() {
		return this.buy;
	}

	public BigDecimal getHigh() {
		return this.high;
	}

	public BigDecimal getLast() {
		return this.last;
	}

	public BigDecimal getLow() {
		return this.low;
	}

	public BigDecimal getSell() {
		return this.sell;
	}

	public BigDecimal getTime() {
		return this.time;
	}

	public BigDecimal getVol() {
		return this.vol;
	}

	public BigDecimal getVwap() {
		return this.vwap;
	}
	
	@Override
	public String toString() {
		return "BitcurexTicker [avg=" + avg + ", buy=" + buy + ", high=" + high
				+ ", last=" + last + ", low=" + low + ", sell=" + sell
				+ ", time=" + time + ", vol=" + vol + ", vwap=" + vwap + "]";
	}
}
