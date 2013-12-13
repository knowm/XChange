package com.xeiam.xchange.cryptsy;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecentTrade {
	String id, time;
	BigDecimal price, quantity, total;

	public RecentTrade(@JsonProperty("id") String id, @JsonProperty("time") String time, @JsonProperty("price") BigDecimal price, @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("total") BigDecimal total) {
		this.id = id;
		this.time = time;
		this.price = price;
		this.quantity = quantity;
		this.total = total;
	}

	public Date getDate() throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(time);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
