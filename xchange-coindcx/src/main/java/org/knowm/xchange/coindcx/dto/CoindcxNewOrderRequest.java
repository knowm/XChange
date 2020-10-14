package org.knowm.xchange.coindcx.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoindcxNewOrderRequest {

	@JsonProperty("side")
	private String side;

	@JsonProperty("order_type")
	private String order_type;

	@JsonProperty("market")
	private String market;

	@JsonProperty("price_per_unit")
	private BigDecimal price_per_unit;

	@JsonProperty("total_quantity")
	private BigDecimal total_quantity;

	@JsonProperty("timestamp")
	private long timestamp;
	
	
	public CoindcxNewOrderRequest(String side,String order_type,String market,BigDecimal price_per_unit,BigDecimal total_quantity,long timestamp) {
		this.side=side;
		this.order_type=order_type;
		this.market=market;
		this.price_per_unit=price_per_unit;
		this.total_quantity=total_quantity;
		this.timestamp=timestamp;
				
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public BigDecimal getPrice_per_unit() {
		return price_per_unit;
	}

	public void setPrice_per_unit(BigDecimal price_per_unit) {
		this.price_per_unit = price_per_unit;
	}

	public BigDecimal getTotal_quantity() {
		return total_quantity;
	}

	public void setTotal_quantity(BigDecimal total_quantity) {
		this.total_quantity = total_quantity;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "CoindcxNewOrderRequest [side=" + side + ", order_type=" + order_type + ", market=" + market
				+ ", price_per_unit=" + price_per_unit + ", total_quantity=" + total_quantity + ", timestamp="
				+ timestamp + "]";
	}

}
