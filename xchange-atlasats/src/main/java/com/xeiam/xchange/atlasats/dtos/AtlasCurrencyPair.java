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
package com.xeiam.xchange.atlasats.dtos;

import java.io.Serializable;

public class AtlasCurrencyPair implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String baseSymbol;
	private String counterSymbol;

	public AtlasCurrencyPair(String baseSymbol, String counterSymbol) {
		this.baseSymbol = baseSymbol;
		this.counterSymbol = counterSymbol;
	}

	public String getBaseSymbol() {
		return baseSymbol;
	}

	public String getCounterSymbol() {
		return counterSymbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((baseSymbol == null) ? 0 : baseSymbol.hashCode());
		result = prime * result
				+ ((counterSymbol == null) ? 0 : counterSymbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AtlasCurrencyPair)) {
			return false;
		}
		AtlasCurrencyPair other = (AtlasCurrencyPair) obj;
		if (baseSymbol == null) {
			if (other.baseSymbol != null) {
				return false;
			}
		} else if (!baseSymbol.equals(other.baseSymbol)) {
			return false;
		}
		if (counterSymbol == null) {
			if (other.counterSymbol != null) {
				return false;
			}
		} else if (!counterSymbol.equals(other.counterSymbol)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AtlasCurrencyPair [baseSymbol=");
		builder.append(baseSymbol);
		builder.append(", counterSymbol=");
		builder.append(counterSymbol);
		builder.append("]");
		return builder.toString();
	}

}
