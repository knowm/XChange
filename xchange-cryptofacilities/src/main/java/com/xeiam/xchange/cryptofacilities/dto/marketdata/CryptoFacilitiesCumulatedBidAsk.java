package com.xeiam.xchange.cryptofacilities.dto.marketdata;

import java.math.BigDecimal;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesCumulatedBidAsk {

	private final BigDecimal price;
	private final BigDecimal quantity;
	
	public CryptoFacilitiesCumulatedBidAsk(BigDecimal price, BigDecimal quantity) {
		super();
		this.price = price;
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	
}
