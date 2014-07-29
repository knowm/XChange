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
