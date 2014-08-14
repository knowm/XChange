package com.xeiam.xchange.atlasats.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

public class AtlasPosition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal realizedProfit;
	private BigDecimal unrealizedProfit;
	private BigDecimal size;

	public BigDecimal getRealizedProfit() {
		return realizedProfit;
	}

	public void setRealizedProfit(BigDecimal realizedProfit) {
		this.realizedProfit = realizedProfit;
	}

	public BigDecimal getUnrealizedProfit() {
		return unrealizedProfit;
	}

	public void setUnrealizedProfit(BigDecimal unrealizedProfit) {
		this.unrealizedProfit = unrealizedProfit;
	}

	public BigDecimal getSize() {
		return size;
	}

	public void setSize(BigDecimal size) {
		this.size = size;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AtlasPosition [realizedProfit=");
		builder.append(realizedProfit);
		builder.append(", unrealizedProfit=");
		builder.append(unrealizedProfit);
		builder.append(", size=");
		builder.append(size);
		builder.append("]");
		return builder.toString();
	}

}
