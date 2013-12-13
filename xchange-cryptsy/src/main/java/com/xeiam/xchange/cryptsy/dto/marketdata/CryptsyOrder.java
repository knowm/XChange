package com.xeiam.xchange.cryptsy.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptsyOrder {

	public String price;
	public String quantity;
	public String total;

	public CryptsyOrder(@JsonProperty("price") String price, @JsonProperty("quantity") String quantity, @JsonProperty("total") String total) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.total = total;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

}
