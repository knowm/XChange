package org.knowm.xchange.ccex.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CCEXTrade {

	private final String id;
	private final String timestamp;
	private final BigDecimal quantity;
	private final BigDecimal price;
	private final BigDecimal total;
	private final String fillType;
	private final String orderType;

	public CCEXTrade(@JsonProperty("Id") String id, @JsonProperty("TimeStamp") String timestamp,
			@JsonProperty("Quantity") BigDecimal quantity, @JsonProperty("Price") BigDecimal price,
			@JsonProperty("Total") BigDecimal total, @JsonProperty("FillType") String fillType,
			@JsonProperty("OrderType") String orderType) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.quantity = quantity;
		this.price = price;
		this.total = total;
		this.fillType = fillType;
		this.orderType = orderType;
	}

	@JsonProperty("Id")
	public String getId() {
		return id;
	}

	@JsonProperty("TimeStamp")
	public String getTimestamp() {
		return timestamp;
	}

	@JsonProperty("Quantity")
	public BigDecimal getQuantity() {
		return quantity;
	}

	@JsonProperty("Price")
	public BigDecimal getPrice() {
		return price;
	}

	@JsonProperty("Total")
	public BigDecimal getTotal() {
		return total;
	}

	@JsonProperty("FillType")
	public String getFillType() {
		return fillType;
	}

	@JsonProperty("OrderType")
	public String getOrderType() {
		return orderType;
	}	
	
}
